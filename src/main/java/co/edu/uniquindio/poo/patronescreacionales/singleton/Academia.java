package co.edu.uniquindio.poo.patronescreacionales.singleton;

import co.edu.uniquindio.poo.enums.EstadoCurso;
import co.edu.uniquindio.poo.enums.Idioma;
import co.edu.uniquindio.poo.model.AsignacionProfesor;
import co.edu.uniquindio.poo.model.Estudiante;
import co.edu.uniquindio.poo.model.IConsultaIngresos;
import co.edu.uniquindio.poo.model.IGestionCursos;
import co.edu.uniquindio.poo.model.IGestionEstudiantes;
import co.edu.uniquindio.poo.model.IGestionMatriculas;
import co.edu.uniquindio.poo.model.IGestionProfesores;
import co.edu.uniquindio.poo.model.IGestionServicios;
import co.edu.uniquindio.poo.model.Profesor;
import co.edu.uniquindio.poo.model.ServicioAdicional;
import co.edu.uniquindio.poo.patronescreacionales.builder.Matricula;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.Curso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Academia implements IGestionEstudiantes, IGestionProfesores, IGestionCursos,
        IGestionServicios, IGestionMatriculas, IConsultaIngresos {
    private static Academia instancia;

    private final String nombreComercial;
    private final String nit;
    private String direccion;
    private String telefono;
    private String correo;
    private String paginaWeb;

    private final List<Estudiante> estudiantes = new ArrayList<>();
    private final List<Profesor> profesores = new ArrayList<>();
    private final List<Curso> cursos = new ArrayList<>();
    private final List<ServicioAdicional> servicios = new ArrayList<>();
    private final List<Matricula> matriculas = new ArrayList<>();
    private final List<AsignacionProfesor> asignaciones = new ArrayList<>();

    private Academia() {
        this.nombreComercial = "LenguajeCafetero";
        this.nit = "900.456.789-1";
        this.direccion = "Cra. 14 # 20-35, Armenia, Quindío";
        this.telefono = "606 745 1234";
        this.correo = "contacto@lenguajecafetero.edu.co";
        this.paginaWeb = "www.lenguajecafetero.edu.co";
    }

    public static synchronized Academia getInstance() {
        if (instancia == null) {
            instancia = new Academia();
        }
        return instancia;
    }

    @Override
    public void registrarEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo");
        }
        if (buscarEstudiante(estudiante.getDocumento()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un estudiante con documento " + estudiante.getDocumento());
        }
        estudiantes.add(estudiante);
    }

    @Override
    public Optional<Estudiante> buscarEstudiante(String documento) {
        if (documento == null) {
            return Optional.empty();
        }
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getDocumento().equals(documento.trim())) {
                return Optional.of(estudiante);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean eliminarEstudiante(String documento) {
        Optional<Estudiante> estudiante = buscarEstudiante(documento);
        if (estudiante.isEmpty()) {
            return false;
        }
        if (!getMatriculasPorEstudiante(documento).isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un estudiante con matrículas registradas");
        }
        return estudiantes.remove(estudiante.get());
    }

    @Override
    public List<Estudiante> getEstudiantes() {
        return Collections.unmodifiableList(estudiantes);
    }

    @Override
    public void registrarProfesor(Profesor profesor) {
        if (profesor == null) {
            throw new IllegalArgumentException("El profesor no puede ser nulo");
        }
        if (buscarProfesor(profesor.getIdentificacion()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un profesor con identificación " + profesor.getIdentificacion());
        }
        profesores.add(profesor);
    }

    @Override
    public Optional<Profesor> buscarProfesor(String identificacion) {
        if (identificacion == null) {
            return Optional.empty();
        }
        for (Profesor profesor : profesores) {
            if (profesor.getIdentificacion().equals(identificacion.trim())) {
                return Optional.of(profesor);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Profesor> getProfesores() {
        return Collections.unmodifiableList(profesores);
    }

    @Override
    public List<Profesor> getProfesoresPorIdioma(Idioma idioma) {
        List<Profesor> resultado = new ArrayList<>();
        for (Profesor profesor : profesores) {
            if (profesor.ensena(idioma)) {
                resultado.add(profesor);
            }
        }
        return resultado;
    }

    @Override
    public void registrarCurso(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("El curso no puede ser nulo");
        }
        if (buscarCurso(curso.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un curso con código " + curso.getCodigo());
        }
        cursos.add(curso);
    }

    @Override
    public Optional<Curso> buscarCurso(String codigo) {
        if (codigo == null) {
            return Optional.empty();
        }
        for (Curso curso : cursos) {
            if (curso.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return Optional.of(curso);
            }
        }
        return Optional.empty();
    }

    @Override
    public void cambiarEstadoCurso(String codigo, EstadoCurso estado) {
        Curso curso = buscarCurso(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe el curso " + codigo));
        curso.setEstado(estado);
    }

    @Override
    public List<Curso> getCursos() {
        return Collections.unmodifiableList(cursos);
    }

    @Override
    public List<Curso> getCursosActivos() {
        List<Curso> activos = new ArrayList<>();
        for (Curso curso : cursos) {
            if (curso.estaActivo()) {
                activos.add(curso);
            }
        }
        return activos;
    }

    @Override
    public void registrarServicio(ServicioAdicional servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio no puede ser nulo");
        }
        if (buscarServicio(servicio.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un servicio con código " + servicio.getCodigo());
        }
        servicios.add(servicio);
    }

    @Override
    public Optional<ServicioAdicional> buscarServicio(String codigo) {
        if (codigo == null) {
            return Optional.empty();
        }
        for (ServicioAdicional servicio : servicios) {
            if (servicio.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return Optional.of(servicio);
            }
        }
        return Optional.empty();
    }

    @Override
    public void cambiarDisponibilidadServicio(String codigo, boolean disponible) {
        ServicioAdicional servicio = buscarServicio(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe el servicio " + codigo));
        servicio.setDisponible(disponible);
    }

    @Override
    public List<ServicioAdicional> getServicios() {
        return Collections.unmodifiableList(servicios);
    }

    @Override
    public List<ServicioAdicional> getServiciosDisponibles() {
        List<ServicioAdicional> disponibles = new ArrayList<>();
        for (ServicioAdicional servicio : servicios) {
            if (servicio.isDisponible()) {
                disponibles.add(servicio);
            }
        }
        return disponibles;
    }

    @Override
    public void registrarMatricula(Matricula matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException("La matrícula no puede ser nula");
        }
        if (buscarMatricula(matricula.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una matrícula con código " + matricula.getCodigo());
        }
        if (buscarEstudiante(matricula.getEstudiante().getDocumento()).isEmpty()) {
            throw new IllegalArgumentException("El estudiante no está registrado en la academia");
        }
        if (buscarCurso(matricula.getCurso().getCodigo()).isEmpty()) {
            throw new IllegalArgumentException("El curso no está registrado en la academia");
        }
        matriculas.add(matricula);
        if (matricula.getAsignacion() != null) {
            asignaciones.add(matricula.getAsignacion());
        }
    }

    @Override
    public Optional<Matricula> buscarMatricula(String codigo) {
        if (codigo == null) {
            return Optional.empty();
        }
        for (Matricula matricula : matriculas) {
            if (matricula.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return Optional.of(matricula);
            }
        }
        return Optional.empty();
    }

    @Override
    public void agregarServicioAMatricula(String codigoMatricula, String codigoServicio) {
        Matricula matricula = buscarMatricula(codigoMatricula)
                .orElseThrow(() -> new IllegalArgumentException("No existe la matrícula " + codigoMatricula));
        ServicioAdicional servicio = buscarServicio(codigoServicio)
                .orElseThrow(() -> new IllegalArgumentException("No existe el servicio " + codigoServicio));
        matricula.agregarServicio(servicio);
    }

    @Override
    public List<Matricula> getMatriculas() {
        return Collections.unmodifiableList(matriculas);
    }

    @Override
    public List<Matricula> getMatriculasPorEstudiante(String documento) {
        List<Matricula> resultado = new ArrayList<>();
        for (Matricula matricula : matriculas) {
            if (matricula.getEstudiante().getDocumento().equals(documento)) {
                resultado.add(matricula);
            }
        }
        return resultado;
    }

    @Override
    public List<AsignacionProfesor> getAsignaciones() {
        return Collections.unmodifiableList(asignaciones);
    }

    @Override
    public String generarCodigoMatricula() {
        int consecutivo = matriculas.size() + 1;
        String codigo = String.format("MAT-%03d", consecutivo);
        while (buscarMatricula(codigo).isPresent()) {
            consecutivo++;
            codigo = String.format("MAT-%03d", consecutivo);
        }
        return codigo;
    }

    @Override
    public double calcularIngresosPorPeriodo(LocalDate fechaInicial, LocalDate fechaFinal) {
        validarPeriodo(fechaInicial, fechaFinal);
        double total = 0;
        for (Matricula matricula : matriculas) {
            if (matricula.estaEnPeriodo(fechaInicial, fechaFinal)) {
                total += matricula.calcularValorTotal();
            }
        }
        return total;
    }

    @Override
    public List<Matricula> getMatriculasPorPeriodo(LocalDate fechaInicial, LocalDate fechaFinal) {
        validarPeriodo(fechaInicial, fechaFinal);
        List<Matricula> resultado = new ArrayList<>();
        for (Matricula matricula : matriculas) {
            if (matricula.estaEnPeriodo(fechaInicial, fechaFinal)) {
                resultado.add(matricula);
            }
        }
        return resultado;
    }

    private void validarPeriodo(LocalDate fechaInicial, LocalDate fechaFinal) {
        if (fechaInicial == null || fechaFinal == null) {
            throw new IllegalArgumentException("Las fechas inicial y final son obligatorias");
        }
        if (fechaInicial.isAfter(fechaFinal)) {
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la fecha final");
        }
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }
}
