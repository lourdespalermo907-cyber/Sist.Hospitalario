package Entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Medico extends Persona implements Serializable {
    private final String matricula;
    private final EspecialidadMedica especialidad;
    private Departamento departamento;

    public Medico(String nombre, String apellido, String dni, LocalDate fechaNacimiento,
                  TipoSangre tipoSangre, String matricula, EspecialidadMedica especialidad) {
        super(nombre, apellido, dni, fechaNacimiento, tipoSangre);
        this.matricula = matricula;
        this.especialidad = especialidad;
    }

    public String getMatricula() { return matricula; }
    public EspecialidadMedica getEspecialidad() { return especialidad; }
    public void setDepartamento(Departamento d) { this.departamento = d; }

    @Override
    public String toString() {
        return "Medico{" +
                "nombre='" + getNombreCompleto() + '\'' +
                ", matricula='" + matricula + '\'' +
                ", especialidad=" + especialidad.getDescripcion() +
                '}';
    }
}
