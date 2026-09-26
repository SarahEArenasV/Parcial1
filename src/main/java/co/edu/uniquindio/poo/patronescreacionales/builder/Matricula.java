package co.edu.uniquindio.poo.patronescreacionales.builder;

import co.edu.uniquindio.poo.model.AsignacionProfesor;
import co.edu.uniquindio.poo.model.Estudiante;
import co.edu.uniquindio.poo.model.IDescuento;
import co.edu.uniquindio.poo.model.ServicioAdicional;
import co.edu.uniquindio.poo.model.SinDescuento;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.Curso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matricula {
    private final String codigo;
    private final LocalDate fecha;
    private final Estudiante estudiante;
    private final Curso curso;
    private final int mesesContratados;
    private final List<ServicioAdicional> servicios = new ArrayList<>();
    private AsignacionProfesor asignacion;
    private IDescuento descuento = new SinDescuento();

    public Matricula(String codigo, LocalDate fecha, Estudiante estudiante, Curso curso, int mesesContratados) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código de la matrícula es obligatorio");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de la matrícula es obligatoria");
        }
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (curso == null) {
            throw new IllegalArgumentException("El curso es obligatorio");
        }
        if (!curso.estaActivo()) {
            throw new IllegalStateException("Solo se puede matricular en cursos activos");
        }
        curso.validarMesesContratados(mesesContratados);
        this.codigo = codigo.trim();
        this.fecha = fecha;
        this.estudiante = estudiante;
        this.curso = curso;
        this.mesesContratados = mesesContratados;
    }

    public void agregarServicio(ServicioAdicional servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio no puede ser nulo");
        }
        if (!servicio.isDisponible()) {
            throw new IllegalStateException("El servicio " + servicio.getNombre() + " no está disponible");
        }
        servicios.add(servicio);
    }

    public void setAsignacion(AsignacionProfesor asignacion) {
        if (asignacion == null) {
            this.asignacion = null;
            return;
        }
        if (!curso.permiteAsignarProfesor()) {
            throw new IllegalStateException("Solo los cursos personalizados permiten asignar profesor");
        }
        if (asignacion.getCurso() != curso || asignacion.getEstudiante() != estudiante) {
            throw new IllegalArgumentException("La asignación no corresponde al estudiante y curso de la matrícula");
        }
        this.asignacion = asignacion;
    }

    public void setDescuento(IDescuento descuento) {
        this.descuento = descuento == null ? new SinDescuento() : descuento;
    }

    public double calcularValorServicios() {
        double total = 0;
        for (ServicioAdicional servicio : servicios) {
            total += servicio.getPrecio();
        }
        return total;
    }

    public double calcularCostoProfesor() {
        return asignacion == null ? 0 : asignacion.calcularCostoSesiones();
    }

    public double calcularSubtotal() {
        return curso.calcularValor(mesesContratados) + calcularCostoProfesor() + calcularValorServicios();
    }

    public double calcularValorTotal() {
        return descuento.aplicar(calcularSubtotal());
    }

    public boolean estaEnPeriodo(LocalDate fechaInicial, LocalDate fechaFinal) {
        return !fecha.isBefore(fechaInicial) && !fecha.isAfter(fechaFinal);
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public int getMesesContratados() {
        return mesesContratados;
    }

    public List<ServicioAdicional> getServicios() {
        return Collections.unmodifiableList(servicios);
    }

    public AsignacionProfesor getAsignacion() {
        return asignacion;
    }

    public IDescuento getDescuento() {
        return descuento;
    }

    @Override
    public String toString() {
        return codigo + " | " + fecha + " | " + estudiante.getNombreCompleto() + " | " + curso.getNombre()
                + " | " + mesesContratados + " mes(es) | $" + String.format("%,.0f", calcularValorTotal());
    }
}
