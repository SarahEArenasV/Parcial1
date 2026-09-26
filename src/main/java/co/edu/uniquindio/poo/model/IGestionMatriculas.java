package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.patronescreacionales.builder.Matricula;

import java.util.List;
import java.util.Optional;

public interface IGestionMatriculas {
    void registrarMatricula(Matricula matricula);

    Optional<Matricula> buscarMatricula(String codigo);

    void agregarServicioAMatricula(String codigoMatricula, String codigoServicio);

    List<Matricula> getMatriculas();

    List<Matricula> getMatriculasPorEstudiante(String documento);

    List<AsignacionProfesor> getAsignaciones();

    String generarCodigoMatricula();
}
