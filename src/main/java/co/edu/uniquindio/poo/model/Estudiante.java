package co.edu.uniquindio.poo.model;

import java.time.LocalDate;

public class Estudiante extends Persona {
    private String correo;
    private int edad;
    private final LocalDate fechaRegistro;

    public Estudiante(String nombreCompleto, String documento, String telefono,
                      String correo, int edad, LocalDate fechaRegistro) {
        super(nombreCompleto, documento, telefono);
        validarCorreo(correo);
        validarEdad(edad);
        if (fechaRegistro == null) {
            throw new IllegalArgumentException("La fecha de registro es obligatoria");
        }
        this.correo = correo.trim();
        this.edad = edad;
        this.fechaRegistro = fechaRegistro;
    }

    private static void validarCorreo(String correo) {
        validarTexto(correo, "correo");
        if (!correo.contains("@") || !correo.contains(".")) {
            throw new IllegalArgumentException("El correo no tiene un formato válido");
        }
    }

    private static void validarEdad(int edad) {
        if (edad <= 0 || edad > 120) {
            throw new IllegalArgumentException("La edad debe estar entre 1 y 120 años");
        }
    }

    public String getNombreCompleto() {
        return getNombre();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        validarCorreo(correo);
        this.correo = correo.trim();
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        validarEdad(edad);
        this.edad = edad;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    @Override
    public String toString() {
        return getDocumento() + " - " + getNombreCompleto();
    }
}
