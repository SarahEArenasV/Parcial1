package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Estudiante;
import co.edu.uniquindio.poo.model.IGestionEstudiantes;
import co.edu.uniquindio.poo.patronescreacionales.builder.EstudianteBuilder;
import co.edu.uniquindio.poo.patronescreacionales.singleton.Academia;

import java.util.List;
import java.util.Optional;

public class EstudianteController {
    private final IGestionEstudiantes gestionEstudiantes;

    public EstudianteController() {
        this(Academia.getInstance());
    }

    public EstudianteController(IGestionEstudiantes gestionEstudiantes) {
        this.gestionEstudiantes = gestionEstudiantes;
    }

    public Estudiante registrarEstudiante(String nombreCompleto, String documento, String telefono,
                                          String correo, String edadTexto) {
        Estudiante estudiante = new EstudianteBuilder()
                .nombreCompleto(nombreCompleto)
                .documento(documento)
                .telefono(telefono)
                .correo(correo)
                .edad(Conversor.aEntero(edadTexto, "edad"))
                .build();
        gestionEstudiantes.registrarEstudiante(estudiante);
        return estudiante;
    }

    public void actualizarEstudiante(Estudiante estudiante, String nombreCompleto, String telefono,
                                     String correo, String edadTexto) {
        if (estudiante == null) {
            throw new IllegalArgumentException("Seleccione un estudiante de la lista");
        }
        gestionEstudiantes.actualizarEstudiante(estudiante.getDocumento(), nombreCompleto, telefono, correo,
                Conversor.aEntero(edadTexto, "edad"));
    }

    public Optional<Estudiante> buscarEstudiante(String documento) {
        if (Conversor.estaVacio(documento)) {
            throw new IllegalArgumentException("Ingrese el documento a buscar");
        }
        return gestionEstudiantes.buscarEstudiante(documento);
    }

    public boolean eliminarEstudiante(String documento) {
        return gestionEstudiantes.eliminarEstudiante(documento);
    }

    public List<Estudiante> listarEstudiantes() {
        return gestionEstudiantes.getEstudiantes();
    }
}
