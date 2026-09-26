package co.edu.uniquindio.poo.enums;

public enum TipoBeneficio {
    PLATAFORMA_VIRTUAL("Acceso a la plataforma virtual", 30000),
    MATERIAL_DIDACTICO("Material didáctico", 40000),
    CLUB_CONVERSACION("Club de conversación", 50000);

    private final String nombre;
    private final double costo;

    TipoBeneficio(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCosto() {
        return costo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
