package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.AsignacionProfesor;
import co.edu.uniquindio.poo.model.DescuentoPorcentaje;
import co.edu.uniquindio.poo.model.Estudiante;
import co.edu.uniquindio.poo.model.IDescuento;
import co.edu.uniquindio.poo.model.IGestionMatriculas;
import co.edu.uniquindio.poo.model.Profesor;
import co.edu.uniquindio.poo.model.ServicioAdicional;
import co.edu.uniquindio.poo.model.SinDescuento;
import co.edu.uniquindio.poo.patronescreacionales.builder.DirectorMatricula;
import co.edu.uniquindio.poo.patronescreacionales.builder.Matricula;
import co.edu.uniquindio.poo.patronescreacionales.builder.MatriculaBuilder;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.Curso;
import co.edu.uniquindio.poo.patronescreacionales.singleton.Academia;

import java.time.LocalDate;
import java.util.List;

public class MatriculaController {
    private final IGestionMatriculas gestionMatriculas;
    private final DirectorMatricula director;

    public MatriculaController() {
        this(Academia.getInstance());
    }

    public MatriculaController(IGestionMatriculas gestionMatriculas) {
        this.gestionMatriculas = gestionMatriculas;
        this.director = new DirectorMatricula(new MatriculaBuilder());
    }

    public Matricula registrarMatricula(Estudiante estudiante, Curso curso, String mesesTexto,
                                        List<ServicioAdicional> servicios, Profesor profesor,
                                        String descuentoTexto) {
        if (estudiante == null) {
            throw new IllegalArgumentException("Seleccione un estudiante");
        }
        if (curso == null) {
            throw new IllegalArgumentException("Seleccione un curso");
        }
        Matricula matricula = director.construirMatricula(gestionMatriculas.generarCodigoMatricula(),
                LocalDate.now(), estudiante, curso, Conversor.aEntero(mesesTexto, "meses contratados"),
                servicios, profesor, crearDescuento(descuentoTexto));
        gestionMatriculas.registrarMatricula(matricula);
        return matricula;
    }

    private IDescuento crearDescuento(String descuentoTexto) {
        if (Conversor.estaVacio(descuentoTexto)) {
            return new SinDescuento();
        }
        double porcentaje = Conversor.aDecimal(descuentoTexto, "descuento (%)");
        return porcentaje == 0 ? new SinDescuento() : new DescuentoPorcentaje(porcentaje);
    }

    public void agregarServicio(Matricula matricula, ServicioAdicional servicio) {
        if (matricula == null || servicio == null) {
            throw new IllegalArgumentException("Seleccione una matrícula y un servicio");
        }
        gestionMatriculas.agregarServicioAMatricula(matricula.getCodigo(), servicio.getCodigo());
    }

    public String obtenerDetalle(Matricula matricula) {
        if (matricula == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Matrícula: ").append(matricula.getCodigo()).append("  |  Fecha: ").append(matricula.getFecha())
                .append("\nEstudiante: ").append(matricula.getEstudiante())
                .append("\nCurso: ").append(matricula.getCurso())
                .append("\nMeses contratados: ").append(matricula.getMesesContratados())
                .append("\nValor curso (mensualidades + beneficios): $")
                .append(formato(matricula.getCurso().calcularValor(matricula.getMesesContratados())));
        if (!matricula.getCurso().getBeneficios().isEmpty()) {
            sb.append("\nBeneficios incluidos: ").append(matricula.getCurso().getBeneficios());
        }
        AsignacionProfesor asignacion = matricula.getAsignacion();
        if (asignacion != null) {
            sb.append("\nProfesor asignado: ").append(asignacion.getProfesor())
                    .append("  |  Costo sesiones: $").append(formato(asignacion.calcularCostoSesiones()));
        }
        sb.append("\nServicios adicionales: ").append(matricula.getServicios().isEmpty() ? "ninguno" : matricula.getServicios())
                .append("\nValor servicios: $").append(formato(matricula.calcularValorServicios()))
                .append("\nSubtotal: $").append(formato(matricula.calcularSubtotal()))
                .append("\n").append(matricula.getDescuento().getDescripcion())
                .append("\nVALOR FINAL A PAGAR: $").append(formato(matricula.calcularValorTotal()));
        return sb.toString();
    }

    private String formato(double valor) {
        return String.format("%,.0f", valor);
    }

    public List<Matricula> listarMatriculas() {
        return gestionMatriculas.getMatriculas();
    }

    public List<AsignacionProfesor> listarAsignaciones() {
        return gestionMatriculas.getAsignaciones();
    }
}
