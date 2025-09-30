package Entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Paciente extends Persona implements Serializable {
    private final String telefono;
    private final String direccion;
    private final HistoriaClinica historiaClinica;

    public Paciente(String nombre, String apellido, String dni, LocalDate fechaNacimiento,
                    TipoSangre tipoSangre, String telefono, String direccion) {
        super(nombre, apellido, dni, fechaNacimiento, tipoSangre);
        this.telefono = telefono;
        this.direccion = direccion;
        this.historiaClinica = new HistoriaClinica(this);
    }

    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public HistoriaClinica getHistoriaClinica() { return historiaClinica; }

    @Override
    public String toString() {
        return "Paciente{" +
                "nombre='" + getNombreCompleto() + '\'' +
                ", DNI='" + getDni() + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
