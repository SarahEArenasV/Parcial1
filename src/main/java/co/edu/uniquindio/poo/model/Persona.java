package co.edu.uniquindio.poo.model;

public abstract class Persona {
    private String nombre;
    private final String documento;
    private String telefono;

    protected Persona(String nombre, String documento, String telefono) {
        validarTexto(nombre, "nombre");
        validarTexto(documento, "documento");
        validarTexto(telefono, "teléfono");
        this.nombre = nombre.trim();
        this.documento = documento.trim();
        this.telefono = telefono.trim();
    }

    protected static void validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarTexto(nombre, "nombre");
        this.nombre = nombre.trim();
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        validarTexto(telefono, "teléfono");
        this.telefono = telefono.trim();
    }
}
