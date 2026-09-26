package co.edu.uniquindio.poo.model;

import java.util.List;
import java.util.Optional;

public interface IGestionEstudiantes {
    void registrarEstudiante(Estudiante estudiante);

    Optional<Estudiante> buscarEstudiante(String documento);

    boolean eliminarEstudiante(String documento);

    List<Estudiante> getEstudiantes();
}
