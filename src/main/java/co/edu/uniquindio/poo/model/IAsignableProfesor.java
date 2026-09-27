package co.edu.uniquindio.poo.model;

import java.time.LocalDate;

public interface IAsignableProfesor {
    AsignacionProfesor crearAsignacion(Estudiante estudiante, Profesor profesor, LocalDate fecha);
}
