package Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sala implements Serializable {
    private final String numero;
    private final String tipo;
    private final Departamento departamento;
    private final List<Cita> citas = new ArrayList<>();

    public Sala(String numero, String tipo, Departamento dep) {
        this.numero = numero;
        this.tipo = tipo;
        this.departamento = dep;
    }

    public String getNumero() { return numero; }
    public String getTipo() { return tipo; }
    public Departamento getDepartamento() { return departamento; }
    public List<Cita> getCitas() { return citas; }

    @Override
    public String toString() {
        return "Sala{" +
                "numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                ", departamento=" + departamento.getNombre() +
                '}';
    }
}
