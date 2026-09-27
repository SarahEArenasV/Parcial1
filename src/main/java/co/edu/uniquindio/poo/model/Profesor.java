package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.Idioma;

public class Profesor extends Persona {
    private final Idioma idioma;
    private double tarifaSesion;

    public Profesor(String identificacion, String nombre, Idioma idioma,
                    String telefono, double tarifaSesion) {
        super(nombre, identificacion, telefono);
        if (idioma == null) {
            throw new IllegalArgumentException("El idioma que enseña es obligatorio");
        }
        validarTarifa(tarifaSesion);
        this.idioma = idioma;
        this.tarifaSesion = tarifaSesion;
    }

    private static void validarTarifa(double tarifa) {
        if (tarifa <= 0) {
            throw new IllegalArgumentException("La tarifa por sesión debe ser mayor a cero");
        }
    }

    public String getIdentificacion() {
        return getDocumento();
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public double getTarifaSesion() {
        return tarifaSesion;
    }

    public void setTarifaSesion(double tarifaSesion) {
        validarTarifa(tarifaSesion);
        this.tarifaSesion = tarifaSesion;
    }

    public boolean ensena(Idioma idioma) {
        return this.idioma == idioma;
    }

    @Override
    public String toString() {
        return getIdentificacion() + " - " + getNombre() + " (" + idioma + ")";
    }
}
