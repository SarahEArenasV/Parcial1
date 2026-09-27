package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.Idioma;

import java.util.List;
import java.util.Optional;

public interface IGestionProfesores {
    void registrarProfesor(Profesor profesor);

    Optional<Profesor> buscarProfesor(String identificacion);

    void actualizarProfesor(String identificacion, String nombre, String telefono, double tarifaSesion);

    List<Profesor> getProfesores();

    List<Profesor> getProfesoresPorIdioma(Idioma idioma);
}
