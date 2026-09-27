package co.edu.uniquindio.poo.patronescreacionales.factorymethod;

import co.edu.uniquindio.poo.enums.EstadoCurso;
import co.edu.uniquindio.poo.enums.Idioma;
import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.enums.TipoCurso;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public abstract class Curso {
    private final String codigo;
    private String nombre;
    private final Idioma idioma;
    private String descripcion;
    private final int duracionMeses;
    private double valorMensual;
    private EstadoCurso estado;
    private final Set<TipoBeneficio> beneficios = EnumSet.noneOf(TipoBeneficio.class);

    protected Curso(String codigo, String nombre, Idioma idioma, String descripcion,
                    int duracionMeses, double valorMensual) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del curso es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del curso es obligatorio");
        }
        if (idioma == null) {
            throw new IllegalArgumentException("El idioma del curso es obligatorio");
        }
        if (duracionMeses <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a cero meses");
        }
        if (valorMensual <= 0) {
            throw new IllegalArgumentException("El valor mensual debe ser mayor a cero");
        }
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.idioma = idioma;
        this.descripcion = descripcion == null ? "" : descripcion.trim();
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = EstadoCurso.ACTIVO;
    }

    protected abstract double calcularValorMensualidades(int mesesContratados);

    public abstract TipoCurso getTipo();

    public boolean permiteAsignarProfesor() {
        return false;
    }

    public String getDetalleEspecifico() {
        return "";
    }

    public final double calcularValor(int mesesContratados) {
        validarMesesContratados(mesesContratados);
        return calcularValorMensualidades(mesesContratados) + calcularCostoBeneficios();
    }

    public void validarMesesContratados(int mesesContratados) {
        if (mesesContratados <= 0 || mesesContratados > duracionMeses) {
            throw new IllegalArgumentException(
                    "Los meses contratados deben estar entre 1 y " + duracionMeses);
        }
    }

    public double calcularCostoBeneficios() {
        double total = 0;
        for (TipoBeneficio beneficio : beneficios) {
            total += beneficio.getCosto();
        }
        return total;
    }

    public void agregarBeneficio(TipoBeneficio beneficio) {
        if (beneficio == null) {
            throw new IllegalArgumentException("El beneficio no puede ser nulo");
        }
        beneficios.add(beneficio);
    }

    public boolean estaActivo() {
        return estado == EstadoCurso.ACTIVO;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del curso es obligatorio");
        }
        this.nombre = nombre.trim();
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? "" : descripcion.trim();
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(double valorMensual) {
        if (valorMensual <= 0) {
            throw new IllegalArgumentException("El valor mensual debe ser mayor a cero");
        }
        this.valorMensual = valorMensual;
    }

    public EstadoCurso getEstado() {
        return estado;
    }

    public void setEstado(EstadoCurso estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }
        this.estado = estado;
    }

    public Set<TipoBeneficio> getBeneficios() {
        return Collections.unmodifiableSet(beneficios);
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " [" + getTipo() + ", " + idioma + ", " + estado + "]";
    }
}
