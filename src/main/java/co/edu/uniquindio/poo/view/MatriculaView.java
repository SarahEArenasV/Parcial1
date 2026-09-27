package co.edu.uniquindio.poo.view;

import co.edu.uniquindio.poo.controller.CursoController;
import co.edu.uniquindio.poo.controller.EstudianteController;
import co.edu.uniquindio.poo.controller.MatriculaController;
import co.edu.uniquindio.poo.controller.ProfesorController;
import co.edu.uniquindio.poo.controller.ServicioController;
import co.edu.uniquindio.poo.model.Estudiante;
import co.edu.uniquindio.poo.model.Profesor;
import co.edu.uniquindio.poo.model.ServicioAdicional;
import co.edu.uniquindio.poo.patronescreacionales.builder.Matricula;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.Curso;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MatriculaView extends VistaBase {
    private final MatriculaController controller = new MatriculaController();
    private final EstudianteController estudianteController = new EstudianteController();
    private final CursoController cursoController = new CursoController();
    private final ProfesorController profesorController = new ProfesorController();
    private final ServicioController servicioController = new ServicioController();

    private final ComboBox<Estudiante> cbEstudiante = new ComboBox<>();
    private final ComboBox<Curso> cbCurso = new ComboBox<>();
    private final TextField txtMeses = new TextField();
    private final ComboBox<Profesor> cbProfesor = new ComboBox<>();
    private final TextField txtDescuento = new TextField();
    private final ListView<ServicioAdicional> listaServicios = new ListView<>();
    private final ListView<Matricula> listaMatriculas = new ListView<>();
    private final ComboBox<ServicioAdicional> cbServicioExtra = new ComboBox<>();
    private final TextArea txtDetalle = new TextArea();

    public MatriculaView() {
        super("Matrículas");
        construir();
        refrescar();
    }

    private void construir() {
        cbCurso.setOnAction(e -> actualizarProfesores());
        cbProfesor.setDisable(true);
        txtDescuento.setPromptText("0");
        listaServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listaServicios.setPrefHeight(100);
        listaServicios.setPrefWidth(420);

        GridPane form = crearFormulario();
        form.addRow(0, new Label("Estudiante:"), cbEstudiante);
        form.addRow(1, new Label("Curso (activo):"), cbCurso);
        form.addRow(2, new Label("Meses contratados:"), txtMeses);
        form.addRow(3, new Label("Profesor (solo personalizado):"), cbProfesor);
        form.addRow(4, new Label("Descuento (%):"), txtDescuento);
        form.addRow(5, new Label("Servicios adicionales\n(Ctrl + clic para varios):"), listaServicios);
        Button btnMatricular = new Button("Registrar matrícula");
        btnMatricular.setOnAction(e -> matricular());
        form.add(btnMatricular, 1, 6);

        listaMatriculas.setPrefHeight(130);
        listaMatriculas.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, matricula) -> txtDetalle.setText(controller.obtenerDetalle(matricula)));

        Button btnAgregarServicio = new Button("Agregar servicio a la matrícula seleccionada");
        btnAgregarServicio.setOnAction(e -> agregarServicio());
        HBox extraBox = new HBox(10, cbServicioExtra, btnAgregarServicio);

        txtDetalle.setEditable(false);
        txtDetalle.setPrefRowCount(12);

        VBox contenido = new VBox(8, titulo("Nueva matrícula"), form, titulo("Matrículas registradas"),
                listaMatriculas, extraBox, txtDetalle);
        contenido.setPadding(new Insets(10));
        setContenido(contenido);
    }

    private void actualizarProfesores() {
        Curso curso = cbCurso.getValue();
        Profesor anterior = cbProfesor.getValue();
        cbProfesor.setValue(null);
        if (curso != null && curso.permiteAsignarProfesor()) {
            cbProfesor.getItems().setAll(profesorController.listarProfesoresPorIdioma(curso.getIdioma()));
            cbProfesor.setDisable(false);
            if (cbProfesor.getItems().contains(anterior)) {
                cbProfesor.setValue(anterior);
            }
        } else {
            cbProfesor.getItems().clear();
            cbProfesor.setDisable(true);
        }
    }

    private void matricular() {
        try {
            List<ServicioAdicional> servicios = new ArrayList<>(listaServicios.getSelectionModel().getSelectedItems());
            Matricula matricula = controller.registrarMatricula(cbEstudiante.getValue(), cbCurso.getValue(),
                    txtMeses.getText(), servicios, cbProfesor.getValue(), txtDescuento.getText());
            mostrarInfo("Matrícula " + matricula.getCodigo() + " registrada. Valor a pagar: $"
                    + String.format("%,.0f", matricula.calcularValorTotal()));
            txtMeses.clear();
            txtDescuento.clear();
            listaServicios.getSelectionModel().clearSelection();
            refrescar();
            listaMatriculas.getSelectionModel().select(matricula);
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void agregarServicio() {
        Matricula seleccionada = listaMatriculas.getSelectionModel().getSelectedItem();
        try {
            controller.agregarServicio(seleccionada, cbServicioExtra.getValue());
            refrescar();
            listaMatriculas.getSelectionModel().select(seleccionada);
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    @Override
    public void refrescar() {
        cbEstudiante.getItems().setAll(estudianteController.listarEstudiantes());
        if (!cbEstudiante.getItems().contains(cbEstudiante.getValue())) {
            cbEstudiante.setValue(null);
        }
        cbCurso.getItems().setAll(cursoController.listarCursosActivos());
        if (!cbCurso.getItems().contains(cbCurso.getValue())) {
            cbCurso.setValue(null);
        }
        List<ServicioAdicional> disponibles = servicioController.listarServiciosDisponibles();
        listaServicios.getItems().setAll(disponibles);
        cbServicioExtra.getItems().setAll(disponibles);
        listaMatriculas.getItems().setAll(controller.listarMatriculas());
        actualizarProfesores();
    }
}
