package co.edu.uniquindio.poo.patronescreacionales.builder;

import co.edu.uniquindio.poo.model.Estudiante;
import co.edu.uniquindio.poo.model.IDescuento;
import co.edu.uniquindio.poo.model.Profesor;
import co.edu.uniquindio.poo.model.ServicioAdicional;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.Curso;

import java.time.LocalDate;

public interface IMatriculaBuilder {
    IMatriculaBuilder reiniciar();

    IMatriculaBuilder conCodigo(String codigo);

    IMatriculaBuilder conFecha(LocalDate fecha);

    IMatriculaBuilder conEstudiante(Estudiante estudiante);

    IMatriculaBuilder conCurso(Curso curso);

    IMatriculaBuilder conMesesContratados(int meses);

    IMatriculaBuilder agregarServicio(ServicioAdicional servicio);

    IMatriculaBuilder conProfesor(Profesor profesor);

    IMatriculaBuilder conDescuento(IDescuento descuento);

    Matricula build();
}
