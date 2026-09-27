package co.edu.uniquindio.poo.model;

import java.util.List;
import java.util.Optional;

public interface IGestionEstudiantes {
    void registrarEstudiante(Estudiante estudiante);

    Optional<Estudiante> buscarEstudiante(String documento);

    void actualizarEstudiante(String documento, String nombreCompleto, String telefono, String correo, int edad);

    boolean eliminarEstudiante(String documento);

    List<Estudiante> getEstudiantes();
}
