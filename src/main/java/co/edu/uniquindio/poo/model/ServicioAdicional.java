package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.TipoServicio;

public class ServicioAdicional {
    private String codigo;
    private final TipoServicio tipo;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;

    public ServicioAdicional(String codigo, TipoServicio tipo, String nombre, String descripcion,
                             double precio, boolean disponible) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del servicio es obligatorio");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de servicio es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del servicio es obligatorio");
        }
        validarPrecio(precio);
        this.codigo = codigo.trim();
        this.tipo = tipo;
        this.nombre = nombre.trim();
        this.descripcion = descripcion == null ? "" : descripcion.trim();
        this.precio = precio;
        this.disponible = disponible;
    }

    private static void validarPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio del servicio debe ser mayor a cero");
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del servicio es obligatorio");
        }
        this.codigo = codigo.trim();
    }

    public TipoServicio getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        validarPrecio(precio);
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " $" + String.format("%,.0f", precio)
                + (disponible ? "" : " (no disponible)");
    }
}
