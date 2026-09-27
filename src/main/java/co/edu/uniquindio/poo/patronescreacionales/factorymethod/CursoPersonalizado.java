package co.edu.uniquindio.poo.patronescreacionales.factorymethod;

import co.edu.uniquindio.poo.enums.Idioma;
import co.edu.uniquindio.poo.enums.NivelReferencia;
import co.edu.uniquindio.poo.enums.TipoCurso;
import co.edu.uniquindio.poo.model.AsignacionProfesor;
import co.edu.uniquindio.poo.model.Estudiante;
import co.edu.uniquindio.poo.model.IAsignableProfesor;
import co.edu.uniquindio.poo.model.Profesor;

import java.time.LocalDate;

public class CursoPersonalizado extends Curso implements IAsignableProfesor {
    private final int cantidadSesiones;
    private final NivelReferencia nivelRequerido;
    private final String objetivosEstudiante;

    public CursoPersonalizado(String codigo, String nombre, Idioma idioma, String descripcion,
                              int duracionMeses, double valorMensual, int cantidadSesiones,
                              NivelReferencia nivelRequerido, String objetivosEstudiante) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual);
        if (cantidadSesiones <= 0) {
            throw new IllegalArgumentException("La cantidad de sesiones debe ser mayor a cero");
        }
        if (nivelRequerido == null) {
            throw new IllegalArgumentException("El nivel de referencia es obligatorio");
        }
        if (objetivosEstudiante == null || objetivosEstudiante.isBlank()) {
            throw new IllegalArgumentException("Los objetivos del estudiante son obligatorios");
        }
        this.cantidadSesiones = cantidadSesiones;
        this.nivelRequerido = nivelRequerido;
        this.objetivosEstudiante = objetivosEstudiante.trim();
    }

    @Override
    protected double calcularValorMensualidades(int mesesContratados) {
        return getValorMensual() * mesesContratados;
    }

    @Override
    public boolean permiteAsignarProfesor() {
        return true;
    }

    @Override
    public AsignacionProfesor crearAsignacion(Estudiante estudiante, Profesor profesor, LocalDate fecha) {
        return new AsignacionProfesor(estudiante, this, profesor, fecha);
    }

    @Override
    public String getDetalleEspecifico() {
        return "Sesiones: " + cantidadSesiones + " | Nivel requerido: " + nivelRequerido
                + " | Objetivos: " + objetivosEstudiante;
    }

    public int getCantidadSesiones() {
        return cantidadSesiones;
    }

    public NivelReferencia getNivelRequerido() {
        return nivelRequerido;
    }

    public String getObjetivosEstudiante() {
        return objetivosEstudiante;
    }

    @Override
    public TipoCurso getTipo() {
        return TipoCurso.PERSONALIZADO;
    }
}
