package Entidades;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CitaManager gestiona todas las operaciones relacionadas con citas médicas:
 * - Programar citas con validaciones de negocio
 * - Consultar citas por paciente, médico y sala
 * - Persistir y recuperar citas en formato CSV
 */
public class CitaManager implements CitaService {
    // Índices para búsquedas rápidas
    private final Map<Paciente, List<Cita>> citasPorPaciente = new ConcurrentHashMap<>();
    private final Map<Medico, List<Cita>> citasPorMedico = new ConcurrentHashMap<>();
    private final Map<Sala, List<Cita>> citasPorSala = new ConcurrentHashMap<>();

    // ===== PROGRAMACIÓN DE CITAS =====
    @Override
    public Cita programarCita(Paciente p, Medico m, Sala s, LocalDateTime fh, BigDecimal costo) throws CitaException {
        // Validaciones de negocio
        if (fh.isBefore(LocalDateTime.now())) throw new CitaException("No se puede programar en el pasado");
        if (costo.compareTo(BigDecimal.ZERO) <= 0) throw new CitaException("El costo debe ser positivo");
        if (!m.getEspecialidad().equals(s.getDepartamento().getEspecialidad()))
            throw new CitaException("Especialidad incompatible entre médico y sala");

        // Validar disponibilidad del médico (ventana de 2 horas)
        if (!esMedicoDisponible(m, fh))
            throw new CitaException("El médico ya tiene una cita en el horario seleccionado");

        // Crear la cita
        Cita nueva = new Cita(p, m, s, fh, costo);

        // Actualizar índices
        citasPorPaciente.computeIfAbsent(p, k -> new ArrayList<>()).add(nueva);
        citasPorMedico.computeIfAbsent(m, k -> new ArrayList<>()).add(nueva);
        citasPorSala.computeIfAbsent(s, k -> new ArrayList<>()).add(nueva);

        return nueva;
    }

    // ===== CONSULTAS =====
    @Override
    public List<Cita> getCitasPorPaciente(Paciente p) {
        return citasPorPaciente.getOrDefault(p, new ArrayList<>());
    }

    public List<Cita> getCitasPorMedico(Medico m) {
        return citasPorMedico.getOrDefault(m, new ArrayList<>());
    }

    public List<Cita> getCitasPorSala(Sala s) {
        return citasPorSala.getOrDefault(s, new ArrayList<>());
    }

    // ===== VALIDACIONES AUXILIARES =====
    private boolean esMedicoDisponible(Medico m, LocalDateTime fechaHora) {
        List<Cita> citas = citasPorMedico.getOrDefault(m, Collections.emptyList());
        for (Cita c : citas) {
            long minutos = Math.abs(java.time.Duration.between(c.getFechaHora(), fechaHora).toMinutes());
            if (minutos < 120) return false; // conflicto dentro de 2 horas
        }
        return true;
    }

    // ===== PERSISTENCIA EN CSV =====
    public void guardarCitas(String archivo) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (List<Cita> lista : citasPorPaciente.values()) {
                for (Cita c : lista) {
                    pw.println(c.toCsvString());
                }
            }
        }
    }

    public void cargarCitas(String archivo,
                            Map<String, Paciente> pacientes,
                            Map<String, Medico> medicos,
                            Map<String, Sala> salas) throws IOException, CitaException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Cita c = Cita.fromCsvString(linea, pacientes, medicos, salas);

                // Actualizar índices
                citasPorPaciente.computeIfAbsent(c.getPaciente(), k -> new ArrayList<>()).add(c);
                citasPorMedico.computeIfAbsent(c.getMedico(), k -> new ArrayList<>()).add(c);
                citasPorSala.computeIfAbsent(c.getSala(), k -> new ArrayList<>()).add(c);
            }
        }
    }
}
