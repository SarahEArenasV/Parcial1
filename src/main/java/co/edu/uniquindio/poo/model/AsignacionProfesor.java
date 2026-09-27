package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.patronescreacionales.factorymethod.CursoPersonalizado;

import java.time.LocalDate;

public class AsignacionProfesor {
    private final Estudiante estudiante;
    private final CursoPersonalizado curso;
    private final Profesor profesor;
    private final LocalDate fechaAsignacion;

    public AsignacionProfesor(Estudiante estudiante, CursoPersonalizado curso,
                              Profesor profesor, LocalDate fechaAsignacion) {
        if (estudiante == null || curso == null || profesor == null || fechaAsignacion == null) {
            throw new IllegalArgumentException("Estudiante, curso, profesor y fecha son obligatorios");
        }
        if (!profesor.ensena(curso.getIdioma())) {
            throw new IllegalArgumentException("El profesor no enseña el idioma del curso ("
                    + curso.getIdioma() + ")");
        }
        this.estudiante = estudiante;
        this.curso = curso;
        this.profesor = profesor;
        this.fechaAsignacion = fechaAsignacion;
    }

    public double calcularCostoSesiones() {
        return profesor.getTarifaSesion() * curso.getCantidadSesiones();
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public CursoPersonalizado getCurso() {
        return curso;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    @Override
    public String toString() {
        return estudiante.getNombreCompleto() + " | " + curso.getNombre() + " | Prof. " + profesor.getNombre();
    }
}
