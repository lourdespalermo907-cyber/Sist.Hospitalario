package Entidades;

import java.io.Serializable;
import java.util.Objects;

public class Matricula implements Serializable {
    private final String numero;

    public Matricula(String numero) {
        Objects.requireNonNull(numero, "La matrícula no puede ser nula");
        if (!numero.matches("MP-\\d{5}")) {
            throw new IllegalArgumentException("La matrícula debe tener formato MP-#####");
        }
        this.numero = numero;
    }

    public String getNumero() { return numero; }

    @Override
    public String toString() {
        return "Matricula{" + "numero='" + numero + '\'' + '}';
    }
}