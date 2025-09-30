package Entidades;

public enum EstadoCita {
    PROGRAMADA("Programada"),
    EN_CURSO("En curso"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada"),
    NO_ASISTIO("No asistió");

    private final String descripcion;
    EstadoCita(String d) { this.descripcion = d; }
    public String getDescripcion() { return descripcion; }
}
