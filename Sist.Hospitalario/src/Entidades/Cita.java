package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class Cita implements Serializable {
    private final Paciente paciente;
    private final Medico medico;
    private final Sala sala;
    private final LocalDateTime fechaHora;
    private final BigDecimal costo;
    private EstadoCita estado = EstadoCita.CANCELADA;
    private String observaciones = "";

    public Cita(Paciente paciente, Medico medico, Sala sala, LocalDateTime fechaHora, BigDecimal costo) {
        this.paciente = paciente;
        this.medico = medico;
        this.sala = sala;
        this.fechaHora = fechaHora;
        this.costo = costo;
    }

    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public Sala getSala() { return sala; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public BigDecimal getCosto() { return costo; }
    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String toCsvString() {
        return paciente.getDni() + "," +
                medico.getDni() + "," +
                sala.getNumero() + "," +
                fechaHora + "," +
                costo + "," +
                estado.name() + "," +
                observaciones.replaceAll(",", ";");
    }

    public static Cita fromCsvString(String line,
                                     Map<String, Paciente> pacientes,
                                     Map<String, Medico> medicos,
                                     Map<String, Sala> salas) throws CitaException {
        try {
            String[] parts = line.split(",", 7);
            Paciente p = pacientes.get(parts[0]);
            Medico m = medicos.get(parts[1]);
            Sala s = salas.get(parts[2]);
            LocalDateTime fh = LocalDateTime.parse(parts[3]);
            BigDecimal costo = new BigDecimal(parts[4]);
            EstadoCita estado = EstadoCita.valueOf(parts[5]);
            String obs = parts.length > 6 ? parts[6] : "";

            Cita c = new Cita(p, m, s, fh, costo);
            c.setEstado(estado);
            c.setObservaciones(obs);
            return c;
        } catch (Exception e) {
            throw new CitaException("Error cargando cita desde CSV: " + e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "Cita{" +
                "paciente=" + paciente.getNombreCompleto() +
                ", medico=" + medico.getNombreCompleto() +
                ", sala=" + sala.getNumero() +
                ", fecha=" + fechaHora +
                ", costo=" + costo +
                ", estado=" + estado +
                '}';
    }
}
