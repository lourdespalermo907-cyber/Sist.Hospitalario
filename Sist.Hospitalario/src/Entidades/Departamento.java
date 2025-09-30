package Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Departamento implements Serializable {
    private final String nombre;
    private final EspecialidadMedica especialidad;
    private Hospital hospital;
    private final List<Medico> medicos = new ArrayList<>();
    private final List<Sala> salas = new ArrayList<>();

    public Departamento(String nombre, EspecialidadMedica especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public void setHospital(Hospital h) { this.hospital = h; }
    public void agregarMedico(Medico m) { medicos.add(m); m.setDepartamento(this); }
    public void crearSala(String numero, String tipo) { salas.add(new Sala(numero, tipo, this)); }

    public String getNombre() { return nombre; }
    public EspecialidadMedica getEspecialidad() { return especialidad; }
    public List<Medico> getMedicos() { return medicos; }
    public List<Sala> getSalas() { return salas; }

    @Override
    public String toString() {
        return "Departamento{" +
                "nombre='" + nombre + '\'' +
                ", especialidad=" + especialidad.getDescripcion() +
                ", medicos=" + medicos.size() +
                ", salas=" + salas.size() +
                '}';
    }
}
