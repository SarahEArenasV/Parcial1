package co.edu.uniquindio.poo.enums;

public enum Idioma {
    INGLES("Inglés"),
    FRANCES("Francés"),
    PORTUGUES("Portugués");

    private final String nombre;

    Idioma(String nombre) {
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
