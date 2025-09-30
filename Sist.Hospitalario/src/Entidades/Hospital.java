package Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hospital implements Serializable {
    private final String nombre;
    private final String direccion;
    private final String telefono;
    private final List<Departamento> departamentos = new ArrayList<>();
    private final List<Paciente> pacientes = new ArrayList<>();

    public Hospital(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public void agregarDepartamento(Departamento dep) {
        departamentos.add(dep);
        dep.setHospital(this);
    }

    public void agregarPaciente(Paciente p) {
        pacientes.add(p);
    }

    public String getNombre() { return nombre; }
    public List<Departamento> getDepartamentos() { return departamentos; }
    public List<Paciente> getPacientes() { return pacientes; }

    @Override
    public String toString() {
        return "Hospital{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", departamentos=" + departamentos.size() +
                ", pacientes=" + pacientes.size() +
                '}';
    }
}
