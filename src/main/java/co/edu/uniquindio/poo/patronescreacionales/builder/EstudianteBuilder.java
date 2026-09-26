package co.edu.uniquindio.poo.patronescreacionales.builder;

import co.edu.uniquindio.poo.model.Estudiante;

import java.time.LocalDate;

public class EstudianteBuilder {
    private String nombreCompleto;
    private String documento;
    private String telefono;
    private String correo;
    private int edad;
    private LocalDate fechaRegistro = LocalDate.now();

    public EstudianteBuilder nombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        return this;
    }

    public EstudianteBuilder documento(String documento) {
        this.documento = documento;
        return this;
    }

    public EstudianteBuilder telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public EstudianteBuilder correo(String correo) {
        this.correo = correo;
        return this;
    }

    public EstudianteBuilder edad(int edad) {
        this.edad = edad;
        return this;
    }

    public EstudianteBuilder fechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        return this;
    }

    public Estudiante build() {
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new IllegalStateException("Falta el nombre completo del estudiante");
        }
        if (documento == null || documento.isBlank()) {
            throw new IllegalStateException("Falta el documento de identidad del estudiante");
        }
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalStateException("Falta el teléfono del estudiante");
        }
        if (correo == null || correo.isBlank()) {
            throw new IllegalStateException("Falta el correo electrónico del estudiante");
        }
        return new Estudiante(nombreCompleto, documento, telefono, correo, edad, fechaRegistro);
    }
}
