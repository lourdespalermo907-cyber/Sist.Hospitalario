package Entidades;

public enum EspecialidadMedica {
    CARDIOLOGIA("Cardiología"),
    NEUROLOGIA("Neurología"),
    PEDIATRIA("Pediatría"),
    TRAUMATOLOGIA("Traumatología"),
    DERMATOLOGIA("Dermatología"),
    GINECOLOGIA("Ginecología"),
    UROLOGIA("Urología"),
    OFTALMOLOGIA("Oftalmología"),
    ENDOCRINOLOGIA("Endocrinología"),
    PSIQUIATRIA("Psiquiatría"),
    CLINICA_GENERAL("Clínica General"),
    ANESTESIOLOGIA("Anestesiología");

    private final String descripcion;
    EspecialidadMedica(String d) { this.descripcion = d; }
    public String getDescripcion() { return descripcion; }
}