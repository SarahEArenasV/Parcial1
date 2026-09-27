package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.EstadoCurso;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.Curso;

import java.util.List;
import java.util.Optional;

public interface IGestionCursos {
    void registrarCurso(Curso curso);

    Optional<Curso> buscarCurso(String codigo);

    void actualizarCurso(String codigo, String nombre, String descripcion, double valorMensual);

    void cambiarEstadoCurso(String codigo, EstadoCurso estado);

    List<Curso> getCursos();

    List<Curso> getCursosActivos();
}
