package co.edu.uniquindio.poo.patronescreacionales.builder;

import co.edu.uniquindio.poo.model.Estudiante;
import co.edu.uniquindio.poo.model.IDescuento;
import co.edu.uniquindio.poo.model.Profesor;
import co.edu.uniquindio.poo.model.ServicioAdicional;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.Curso;

import java.time.LocalDate;
import java.util.List;

public class DirectorMatricula {
    private final IMatriculaBuilder builder;

    public DirectorMatricula(IMatriculaBuilder builder) {
        this.builder = builder;
    }

    public Matricula construirMatricula(String codigo, LocalDate fecha, Estudiante estudiante, Curso curso,
                                        int meses, List<ServicioAdicional> servicios, Profesor profesor,
                                        IDescuento descuento) {
        if (profesor != null) {
            return construirMatriculaPersonalizada(codigo, fecha, estudiante, curso, meses, profesor,
                    servicios, descuento);
        }
        return construirMatriculaCompleta(codigo, fecha, estudiante, curso, meses, servicios, descuento);
    }

    public Matricula construirMatriculaBasica(String codigo, LocalDate fecha, Estudiante estudiante,
                                              Curso curso, int meses) {
        return builder.reiniciar()
                .conCodigo(codigo)
                .conFecha(fecha)
                .conEstudiante(estudiante)
                .conCurso(curso)
                .conMesesContratados(meses)
                .build();
    }

    public Matricula construirMatriculaCompleta(String codigo, LocalDate fecha, Estudiante estudiante,
                                                Curso curso, int meses, List<ServicioAdicional> servicios,
                                                IDescuento descuento) {
        builder.reiniciar()
                .conCodigo(codigo)
                .conFecha(fecha)
                .conEstudiante(estudiante)
                .conCurso(curso)
                .conMesesContratados(meses)
                .conDescuento(descuento);
        agregarServicios(servicios);
        return builder.build();
    }

    public Matricula construirMatriculaPersonalizada(String codigo, LocalDate fecha, Estudiante estudiante,
                                                     Curso curso, int meses, Profesor profesor,
                                                     List<ServicioAdicional> servicios, IDescuento descuento) {
        if (profesor == null) {
            throw new IllegalArgumentException("La matrícula personalizada requiere un profesor");
        }
        builder.reiniciar()
                .conCodigo(codigo)
                .conFecha(fecha)
                .conEstudiante(estudiante)
                .conCurso(curso)
                .conMesesContratados(meses)
                .conProfesor(profesor)
                .conDescuento(descuento);
        agregarServicios(servicios);
        return builder.build();
    }

    private void agregarServicios(List<ServicioAdicional> servicios) {
        if (servicios != null) {
            for (ServicioAdicional servicio : servicios) {
                builder.agregarServicio(servicio);
            }
        }
    }
}
