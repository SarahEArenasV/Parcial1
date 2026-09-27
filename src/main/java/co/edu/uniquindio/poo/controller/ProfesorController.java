package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.enums.Idioma;
import co.edu.uniquindio.poo.model.IGestionProfesores;
import co.edu.uniquindio.poo.model.Profesor;
import co.edu.uniquindio.poo.patronescreacionales.singleton.Academia;

import java.util.List;

public class ProfesorController {
    private final IGestionProfesores gestionProfesores;

    public ProfesorController() {
        this(Academia.getInstance());
    }

    public ProfesorController(IGestionProfesores gestionProfesores) {
        this.gestionProfesores = gestionProfesores;
    }

    public Profesor registrarProfesor(String identificacion, String nombre, Idioma idioma,
                                      String telefono, String tarifaTexto) {
        Profesor profesor = new Profesor(identificacion, nombre, idioma, telefono,
                Conversor.aDecimal(tarifaTexto, "tarifa por sesión"));
        gestionProfesores.registrarProfesor(profesor);
        return profesor;
    }

    public void actualizarProfesor(Profesor profesor, String nombre, String telefono, String tarifaTexto) {
        if (profesor == null) {
            throw new IllegalArgumentException("Seleccione un profesor de la lista");
        }
        gestionProfesores.actualizarProfesor(profesor.getIdentificacion(), nombre, telefono,
                Conversor.aDecimal(tarifaTexto, "tarifa por sesión"));
    }

    public List<Profesor> listarProfesores() {
        return gestionProfesores.getProfesores();
    }

    public List<Profesor> listarProfesoresPorIdioma(Idioma idioma) {
        return gestionProfesores.getProfesoresPorIdioma(idioma);
    }
}
