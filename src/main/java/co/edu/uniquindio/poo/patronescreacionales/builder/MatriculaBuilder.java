package co.edu.uniquindio.poo.patronescreacionales.builder;

import co.edu.uniquindio.poo.model.Estudiante;
import co.edu.uniquindio.poo.model.IDescuento;
import co.edu.uniquindio.poo.model.Profesor;
import co.edu.uniquindio.poo.model.ServicioAdicional;
import co.edu.uniquindio.poo.model.SinDescuento;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.Curso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatriculaBuilder implements IMatriculaBuilder {
    private String codigo;
    private LocalDate fecha;
    private Estudiante estudiante;
    private Curso curso;
    private int mesesContratados;
    private List<ServicioAdicional> servicios;
    private Profesor profesor;
    private IDescuento descuento;

    public MatriculaBuilder() {
        reiniciar();
    }

    @Override
    public IMatriculaBuilder reiniciar() {
        codigo = null;
        fecha = LocalDate.now();
        estudiante = null;
        curso = null;
        mesesContratados = 0;
        servicios = new ArrayList<>();
        profesor = null;
        descuento = new SinDescuento();
        return this;
    }

    @Override
    public IMatriculaBuilder conCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    @Override
    public IMatriculaBuilder conFecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    @Override
    public IMatriculaBuilder conEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        return this;
    }

    @Override
    public IMatriculaBuilder conCurso(Curso curso) {
        this.curso = curso;
        return this;
    }

    @Override
    public IMatriculaBuilder conMesesContratados(int meses) {
        this.mesesContratados = meses;
        return this;
    }

    @Override
    public IMatriculaBuilder agregarServicio(ServicioAdicional servicio) {
        if (servicio != null) {
            servicios.add(servicio);
        }
        return this;
    }

    @Override
    public IMatriculaBuilder conProfesor(Profesor profesor) {
        this.profesor = profesor;
        return this;
    }

    @Override
    public IMatriculaBuilder conDescuento(IDescuento descuento) {
        this.descuento = descuento;
        return this;
    }

    @Override
    public Matricula build() {
        validarPartesObligatorias();
        Matricula matricula = new Matricula(codigo, fecha, estudiante, curso, mesesContratados);
        for (ServicioAdicional servicio : servicios) {
            matricula.agregarServicio(servicio);
        }
        if (profesor != null) {
            matricula.setAsignacion(curso.crearAsignacion(estudiante, profesor, fecha));
        }
        matricula.setDescuento(descuento);
        Matricula resultado = matricula;
        reiniciar();
        return resultado;
    }

    private void validarPartesObligatorias() {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalStateException("Falta el código de la matrícula");
        }
        if (estudiante == null) {
            throw new IllegalStateException("Falta el estudiante de la matrícula");
        }
        if (curso == null) {
            throw new IllegalStateException("Falta el curso de la matrícula");
        }
        if (mesesContratados <= 0) {
            throw new IllegalStateException("Falta la duración contratada de la matrícula");
        }
        if (profesor != null && !curso.permiteAsignarProfesor()) {
            throw new IllegalStateException("Solo los cursos personalizados permiten asignar profesor");
        }
    }
}
