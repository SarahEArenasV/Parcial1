package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.patronescreacionales.builder.Matricula;

import java.time.LocalDate;
import java.util.List;

public interface IConsultaIngresos {
    double calcularIngresosPorPeriodo(LocalDate fechaInicial, LocalDate fechaFinal);

    List<Matricula> getMatriculasPorPeriodo(LocalDate fechaInicial, LocalDate fechaFinal);
}
