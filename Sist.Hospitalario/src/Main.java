import Entidades.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("=====|| SISTEMA DE GESTIÓN HOSPITALARIA ||=====\n");

        try {
            //inicializo el hospital
            Hospital hospital = inicializarHospital();

            //crar mdicos
            List<Medico> medicos = crearMedicos(hospital);

            //regustro de pacientes
            List<Paciente> pacientes = registrarPacientes(hospital);

            //gestion de citas medicas
            CitaManager citaManager = new CitaManager();
            programarCitas(citaManager, medicos, pacientes, hospital);

           //metodos de visualizacion
            mostrarInformacionCompleta(hospital, citaManager);

            //pruebas de persistencia
            probarPersistencia(citaManager, pacientes, medicos, hospital);

            //pruebas de validacion
            ejecutarPruebasValidacion(citaManager, medicos, pacientes, hospital);

            //estadisticas finales
            mostrarEstadisticasFinales(hospital);

            System.out.println("\n===== ||SISTEMA EJECUTADO EXITOSAMENTE ||=====");

        } catch (Exception e) {
            System.err.println("Error en el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // metodos de inicializacion
    private static Hospital inicializarHospital() {
        System.out.println("Inicializando hospital y departamentos...");

        Hospital hospital = new Hospital("Hospital central", "Calle 123", "011-1111-1111");

        Departamento cardio = new Departamento("Cardiología", EspecialidadMedica.CARDIOLOGIA);
        Departamento pedi = new Departamento("Pediatría", EspecialidadMedica.PEDIATRIA);
        Departamento trauma = new Departamento("Traumatología", EspecialidadMedica.TRAUMATOLOGIA);

        hospital.agregarDepartamento(cardio);
        hospital.agregarDepartamento(pedi);
        hospital.agregarDepartamento(trauma);

        // Crear salas
        cardio.crearSala("CARD-101", "Consultorio");
        cardio.crearSala("CARD-102", "Quirófano");
        pedi.crearSala("PED-201", "Guardia");
        trauma.crearSala("TRAU-301", "Emergencias");

        System.out.println("Hospital inicializado con " + hospital.getDepartamentos().size() + " departamentos\n");
        return hospital;
    }

    private static List<Medico> crearMedicos(Hospital hospital) {
        System.out.println("Registrando médicos especialistas...");

        List<Medico> medicos = new ArrayList<>();

        Medico cardiologo = new Medico("Laura", "Noesta", "11111111", LocalDate.of(1980, 5, 12),
                TipoSangre.A_POSITIVO, "MP-11111", EspecialidadMedica.CARDIOLOGIA);
        Medico pediatra = new Medico("Armando, Esteban", "Quito", "22222222", LocalDate.of(1975, 3, 8),
                TipoSangre.O_POSITIVO, "MP-22222", EspecialidadMedica.PEDIATRIA);
        Medico traumatologo = new Medico("Laura", "Sefue", "33333333", LocalDate.of(1985, 7, 20),
                TipoSangre.B_NEGATIVO, "MP-33333", EspecialidadMedica.TRAUMATOLOGIA);

        for (Departamento dep : hospital.getDepartamentos()) {
            switch (dep.getEspecialidad()) {
                case CARDIOLOGIA -> { dep.agregarMedico(cardiologo); medicos.add(cardiologo); }
                case PEDIATRIA -> { dep.agregarMedico(pediatra); medicos.add(pediatra); }
                case TRAUMATOLOGIA -> { dep.agregarMedico(traumatologo); medicos.add(traumatologo); }
            }
        }

        System.out.println("Registrados " + medicos.size() + " médicos especialistas\n");
        return medicos;
    }

    private static List<Paciente> registrarPacientes(Hospital hospital) {
        System.out.println("Registrando pacientes...");

        List<Paciente> pacientes = new ArrayList<>();

        Paciente p1 = new Paciente("Ana", "Gomez", "11111111", LocalDate.of(1990, 1, 20),
                TipoSangre.B_POSITIVO, "011-1111-1111", "avenida 123");
        Paciente p2 = new Paciente("JOSE", "Fernandez", "55555555", LocalDate.of(2010, 10, 15),
                TipoSangre.AB_NEGATIVO, "011-2222-2222", "Cavenida 456");
        Paciente p3 = new Paciente("Maria", "Jose", "66666666", LocalDate.of(1985, 6, 5),
                TipoSangre.O_POSITIVO, "011-3333-3333", "Calle Principal 123");

        hospital.agregarPaciente(p1);
        hospital.agregarPaciente(p2);
        hospital.agregarPaciente(p3);

        pacientes.addAll(List.of(p1, p2, p3));

        // Configurar historias clínicas
        p1.getHistoriaClinica().agregarDiagnostico("Hipertensión arterial");
        p2.getHistoriaClinica().agregarDiagnostico("Control pediátrico rutinario");
        p3.getHistoriaClinica().agregarDiagnostico("Fractura de tobillo");

        System.out.println("Registrados " + pacientes.size() + " pacientes con historias clínicas\n");
        return pacientes;
    }

    // gestion de citas medicads
    private static void programarCitas(CitaManager cm, List<Medico> medicos, List<Paciente> pacientes, Hospital hospital) throws CitaException {
        System.out.println("Programando citas médicas.");

        Map<EspecialidadMedica, Sala> salas = obtenerSalasPorEspecialidad(hospital);
        LocalDateTime base = LocalDateTime.now().plusDays(1);

        Cita c1 = cm.programarCita(pacientes.get(0), medicos.get(0), salas.get(EspecialidadMedica.CARDIOLOGIA),
                base.withHour(10), new BigDecimal("111111"));
        c1.setObservaciones("Chequeo cardiológico");
        c1.setEstado(EstadoCita.EN_CURSO);

        Cita c2 = cm.programarCita(pacientes.get(1), medicos.get(1), salas.get(EspecialidadMedica.PEDIATRIA),
                base.plusDays(1).withHour(14), new BigDecimal("11111"));
        c2.setObservaciones("Control pediátrico");

        Cita c3 = cm.programarCita(pacientes.get(2), medicos.get(2), salas.get(EspecialidadMedica.TRAUMATOLOGIA),
                base.plusDays(2).withHour(9), new BigDecimal("111111"));
        c3.setObservaciones("Revisión traumatológica");

        System.out.println("Programadas 3 citas médicas exitosamente\n");
    }

    // metodos de visualizacion
    private static void mostrarInformacionCompleta(Hospital hospital, CitaManager cm) {
        mostrarInformacionHospital(hospital);
        mostrarDepartamentosYPersonal(hospital);
        mostrarPacientesEHistorias(hospital);
        mostrarCitasProgramadas(hospital, cm);
    }

    private static void mostrarInformacionHospital(Hospital hospital) {
        System.out.println("===== ||INFORMACIÓN DEL HOSPITAL|| =====");
        System.out.println(hospital);
        System.out.println("Departamentos: " + hospital.getDepartamentos().size());
        System.out.println("Pacientes registrados: " + hospital.getPacientes().size());
        System.out.println();
    }

    private static void mostrarDepartamentosYPersonal(Hospital hospital) {
        System.out.println("===== ||DEPARTAMENTOS Y PERSONAL ||=====");
        for (Departamento dep : hospital.getDepartamentos()) {
            System.out.println(dep);

            System.out.println("  Médicos:");
            for (Medico m : dep.getMedicos()) {
                System.out.println("    " + m);
            }

            System.out.println("  Salas:");
            for (Sala s : dep.getSalas()) {
                System.out.println("    " + s);
            }
            System.out.println();
        }
    }

    private static void mostrarPacientesEHistorias(Hospital hospital) {
        System.out.println("===== ||PACIENTES E HISTORIAS CLÍNICAS|| =====");
        for (Paciente p : hospital.getPacientes()) {
            System.out.println(p);
            HistoriaClinica h = p.getHistoriaClinica();
            System.out.println("  Historia: " + h.getNumeroHistoria() + " | Edad: " + p.getEdad());
            if (!h.getDiagnosticos().isEmpty()) System.out.println("  Diagnósticos: " + h.getDiagnosticos());
            if (!h.getTratamientos().isEmpty()) System.out.println("  Tratamientos: " + h.getTratamientos());
            if (!h.getAlergias().isEmpty()) System.out.println("  Alergias: " + h.getAlergias());
            System.out.println();
        }
    }

    private static void mostrarCitasProgramadas(Hospital hospital, CitaManager cm) {
        System.out.println("===== ||CITAS PROGRAMADAS|| =====");
        for (Paciente p : hospital.getPacientes()) {
            List<Cita> citas = cm.getCitasPorPaciente(p);
            if (!citas.isEmpty()) {
                System.out.println("Citas de " + p.getNombreCompleto() + ":");
                for (Cita c : citas) {
                    System.out.println("  " + c);
                    if (!c.getObservaciones().isEmpty()) {
                        System.out.println("    Observaciones: " + c.getObservaciones());
                    }
                }
                System.out.println();
            }
        }
    }

    // pruebas de persistencia
    private static void probarPersistencia(CitaManager cm, List<Paciente> pacientes, List<Medico> medicos, Hospital hospital) {
        System.out.println("=====|| PRUEBA DE PERSISTENCIA||=====");
        try {
            String archivo = "citas.csv";
            cm.guardarCitas(archivo);
            System.out.println("Citas guardadas en " + archivo);

            CitaManager nuevoCM = new CitaManager();
            nuevoCM.cargarCitas(archivo, crearMapaPacientes(pacientes), crearMapaMedicos(medicos), crearMapaSalas(hospital));
            System.out.println("Citas cargadas exitosamente");
        } catch (Exception e) {
            System.err.println("Error en persistencia: " + e.getMessage());
        }
        System.out.println();
    }

    // pruebas de validacion
    private static void ejecutarPruebasValidacion(CitaManager cm, List<Medico> medicos, List<Paciente> pacientes, Hospital hospital) {
        System.out.println("===== ||PRUEBAS DE VALIDACIÓN|| =====");

        try {
            cm.programarCita(pacientes.get(0), medicos.get(0),
                    hospital.getDepartamentos().get(0).getSalas().get(0),
                    LocalDateTime.now().minusDays(1), new BigDecimal("1000"));
            System.out.println("ERROR-< Se permitió cita en el pasado");
        } catch (CitaException e) {
            System.out.println(" Validación fecha pasada: " + e.getMessage());
        }

        try {
            cm.programarCita(pacientes.get(0), medicos.get(0),
                    hospital.getDepartamentos().get(0).getSalas().get(0),
                    LocalDateTime.now().plusDays(1), new BigDecimal("-500"));
            System.out.println("ERROR-> Se permitió costo negativo");
        } catch (CitaException e) {
            System.out.println("Validación costo negativo: " + e.getMessage());
        }

        System.out.println();
    }

    // estadisticas
    private static void mostrarEstadisticasFinales(Hospital hospital) {
        System.out.println("===== ||ESTADÍSTICAS FINALES|| =====");

        int totalMedicos = hospital.getDepartamentos().stream().mapToInt(d -> d.getMedicos().size()).sum();
        int totalSalas = hospital.getDepartamentos().stream().mapToInt(d -> d.getSalas().size()).sum();

        System.out.println("Departamentos: " + hospital.getDepartamentos().size());
        System.out.println("Pacientes: " + hospital.getPacientes().size());
        System.out.println("Médicos: " + totalMedicos);
        System.out.println("Salas: " + totalSalas);
    }

    // meotodos auxiliares(map helpers)
    //funciones que arman tablas de referencia rápida para que, cuando cargues el CSV, puedas reconstruir la cita sabiendo a qué paciente, médico o sala pertenece.
    private static Map<EspecialidadMedica, Sala> obtenerSalasPorEspecialidad(Hospital hospital) {
        Map<EspecialidadMedica, Sala> map = new HashMap<>();
        for (Departamento dep : hospital.getDepartamentos()) {
            if (!dep.getSalas().isEmpty()) {
                map.put(dep.getEspecialidad(), dep.getSalas().get(0));
            }
        }
        return map;
    }

    private static Map<String, Paciente> crearMapaPacientes(List<Paciente> pacientes) {
        Map<String, Paciente> map = new HashMap<>();
        for (Paciente p : pacientes) map.put(p.getDni(), p);
        return map;
    }

    private static Map<String, Medico> crearMapaMedicos(List<Medico> medicos) {
        Map<String, Medico> map = new HashMap<>();
        for (Medico m : medicos) map.put(m.getDni(), m);
        return map;
    }

    private static Map<String, Sala> crearMapaSalas(Hospital hospital) {
        Map<String, Sala> map = new HashMap<>();
        for (Departamento d : hospital.getDepartamentos()) {
            for (Sala s : d.getSalas()) map.put(s.getNumero(), s);
        }
        return map;
    }
}
