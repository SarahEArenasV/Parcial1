package co.edu.uniquindio.poo.enums;

public enum TipoServicio {
    SIMULACRO_EXAMEN("Simulacro de examen de certificación"),
    TUTORIA_REFUERZO("Tutoría de refuerzo"),
    MATERIAL_IMPRESO("Material impreso"),
    TALLER_CONVERSACION("Taller de conversación");

    private final String nombre;

    TipoServicio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
