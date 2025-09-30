package Entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistoriaClinica implements Serializable {
    private final String numeroHistoria;
    private final Paciente paciente;
    private final LocalDateTime fechaCreacion;
    private final List<String> diagnosticos = new ArrayList<>();
    private final List<String> tratamientos = new ArrayList<>();
    private final List<String> alergias = new ArrayList<>();

    public HistoriaClinica(Paciente paciente) {
        this.paciente = paciente;
        this.fechaCreacion = LocalDateTime.now();
        this.numeroHistoria = "HC-" + paciente.getDni() + "-" + fechaCreacion.getYear();
    }

    public String getNumeroHistoria() { return numeroHistoria; }
    public List<String> getDiagnosticos() { return diagnosticos; }
    public List<String> getTratamientos() { return tratamientos; }
    public List<String> getAlergias() { return alergias; }

    public void agregarDiagnostico(String d) { diagnosticos.add(d); }
    public void agregarTratamiento(String t) { tratamientos.add(t); }
    public void agregarAlergia(String a) { alergias.add(a); }

    @Override
    public String toString() {
        return "HistoriaClinica{" +
                "numeroHistoria='" + numeroHistoria + '\'' +
                ", diagnosticos=" + diagnosticos +
                ", tratamientos=" + tratamientos +
                ", alergias=" + alergias +
                '}';
    }
}
