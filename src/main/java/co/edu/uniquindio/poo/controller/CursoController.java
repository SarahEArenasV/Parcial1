package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.enums.EstadoCurso;
import co.edu.uniquindio.poo.enums.Idioma;
import co.edu.uniquindio.poo.enums.NivelReferencia;
import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.enums.TipoCurso;
import co.edu.uniquindio.poo.model.IGestionCursos;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.CreadorCurso;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.CreadorCursoIntensivo;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.CreadorCursoPersonalizado;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.CreadorCursoRegular;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.Curso;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.DatosCurso;
import co.edu.uniquindio.poo.patronescreacionales.singleton.Academia;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CursoController {
    private final IGestionCursos gestionCursos;
    private final Map<TipoCurso, CreadorCurso> creadores = new EnumMap<>(TipoCurso.class);

    public CursoController() {
        this(Academia.getInstance(),
                List.of(new CreadorCursoRegular(), new CreadorCursoIntensivo(), new CreadorCursoPersonalizado()));
    }

    public CursoController(IGestionCursos gestionCursos, List<CreadorCurso> creadoresDisponibles) {
        this.gestionCursos = gestionCursos;
        for (CreadorCurso creador : creadoresDisponibles) {
            creadores.put(creador.getTipoCurso(), creador);
        }
    }

    public Curso crearCurso(TipoCurso tipo, String codigo, String nombre, Idioma idioma, String descripcion,
                            String duracionTexto, String valorMensualTexto, Set<TipoBeneficio> beneficios,
                            String sesionesTexto, NivelReferencia nivel, String objetivos) {
        CreadorCurso creador = creadores.get(tipo);
        if (creador == null) {
            throw new IllegalArgumentException("Seleccione un tipo de curso válido");
        }
        int sesiones = Conversor.estaVacio(sesionesTexto) ? 0 : Conversor.aEntero(sesionesTexto, "cantidad de sesiones");
        DatosCurso datos = new DatosCurso(codigo, nombre, idioma, descripcion,
                Conversor.aEntero(duracionTexto, "duración en meses"),
                Conversor.aDecimal(valorMensualTexto, "valor mensual"),
                beneficios, sesiones, nivel, objetivos);
        Curso curso = creador.crear(datos);
        gestionCursos.registrarCurso(curso);
        return curso;
    }

    public void actualizarCurso(Curso curso, String nombre, String descripcion, String valorMensualTexto) {
        if (curso == null) {
            throw new IllegalArgumentException("Seleccione un curso de la lista");
        }
        gestionCursos.actualizarCurso(curso.getCodigo(), nombre, descripcion,
                Conversor.aDecimal(valorMensualTexto, "valor mensual"));
    }

    public void cambiarEstado(Curso curso, EstadoCurso estado) {
        if (curso == null) {
            throw new IllegalArgumentException("Seleccione un curso");
        }
        gestionCursos.cambiarEstadoCurso(curso.getCodigo(), estado);
    }

    public List<Curso> listarCursos() {
        return gestionCursos.getCursos();
    }

    public List<Curso> listarCursosActivos() {
        return gestionCursos.getCursosActivos();
    }
}
