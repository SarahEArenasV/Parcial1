package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.IConsultaIngresos;
import co.edu.uniquindio.poo.patronescreacionales.builder.Matricula;
import co.edu.uniquindio.poo.patronescreacionales.singleton.Academia;

import java.time.LocalDate;
import java.util.List;

public class ReporteController {
    private final IConsultaIngresos consultaIngresos;

    public ReporteController() {
        this(Academia.getInstance());
    }

    public ReporteController(IConsultaIngresos consultaIngresos) {
        this.consultaIngresos = consultaIngresos;
    }

    public double calcularIngresos(LocalDate fechaInicial, LocalDate fechaFinal) {
        return consultaIngresos.calcularIngresosPorPeriodo(fechaInicial, fechaFinal);
    }

    public List<Matricula> listarMatriculasDelPeriodo(LocalDate fechaInicial, LocalDate fechaFinal) {
        return consultaIngresos.getMatriculasPorPeriodo(fechaInicial, fechaFinal);
    }
}
