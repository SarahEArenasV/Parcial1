package co.edu.uniquindio.poo.view;

import co.edu.uniquindio.poo.controller.MatriculaController;
import co.edu.uniquindio.poo.controller.ProfesorController;
import co.edu.uniquindio.poo.enums.Idioma;
import co.edu.uniquindio.poo.model.AsignacionProfesor;
import co.edu.uniquindio.poo.model.Profesor;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfesorView extends VistaBase {
    private final ProfesorController controller = new ProfesorController();
    private final MatriculaController matriculaController = new MatriculaController();

    private final TextField txtId = new TextField();
    private final TextField txtNombre = new TextField();
    private final ComboBox<Idioma> cbIdioma = new ComboBox<>();
    private final TextField txtTelefono = new TextField();
    private final TextField txtTarifa = new TextField();
    private final ListView<Profesor> listaProfesores = new ListView<>();
    private final ListView<AsignacionProfesor> listaAsignaciones = new ListView<>();

    public ProfesorView() {
        super("Profesores");
        construir();
        refrescar();
    }

    private void construir() {
        cbIdioma.getItems().setAll(Idioma.values());

        GridPane form = crearFormulario();
        form.addRow(0, new Label("Identificación:"), txtId);
        form.addRow(1, new Label("Nombre:"), txtNombre);
        form.addRow(2, new Label("Idioma que enseña:"), cbIdioma);
        form.addRow(3, new Label("Teléfono:"), txtTelefono);
        form.addRow(4, new Label("Tarifa por sesión:"), txtTarifa);
        Button btnRegistrar = new Button("Registrar profesor");
        btnRegistrar.setOnAction(e -> registrar());
        Button btnActualizar = new Button("Actualizar seleccionado");
        btnActualizar.setOnAction(e -> actualizar());
        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setOnAction(e -> limpiar());
        form.add(new HBox(10, btnRegistrar, btnActualizar, btnLimpiar), 1, 5);

        listaProfesores.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, profesor) -> cargarEnFormulario(profesor));

        listaProfesores.setPrefHeight(150);
        listaAsignaciones.setPrefHeight(150);

        VBox contenido = new VBox(10, titulo("Registro de profesores"), form,
                titulo("Profesores registrados"), listaProfesores,
                titulo("Asignaciones (estudiante | curso | profesor)"), listaAsignaciones);
        contenido.setPadding(new Insets(10));
        setContenido(contenido);
    }

    private void registrar() {
        try {
            Profesor profesor = controller.registrarProfesor(txtId.getText(), txtNombre.getText(),
                    cbIdioma.getValue(), txtTelefono.getText(), txtTarifa.getText());
            mostrarInfo("Profesor registrado: " + profesor.getNombre());
            limpiar();
            refrescar();
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void cargarEnFormulario(Profesor profesor) {
        if (profesor == null) {
            return;
        }
        txtId.setText(profesor.getIdentificacion());
        txtNombre.setText(profesor.getNombre());
        cbIdioma.setValue(profesor.getIdioma());
        txtTelefono.setText(profesor.getTelefono());
        txtTarifa.setText(String.valueOf(profesor.getTarifaSesion()));
    }

    private void actualizar() {
        Profesor seleccionado = listaProfesores.getSelectionModel().getSelectedItem();
        try {
            controller.actualizarProfesor(seleccionado, txtNombre.getText(), txtTelefono.getText(),
                    txtTarifa.getText());
            mostrarInfo("Profesor actualizado. La identificación y el idioma no se pueden modificar.");
            refrescar();
            limpiar();
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void limpiar() {
        listaProfesores.getSelectionModel().clearSelection();
        txtId.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtTarifa.clear();
        cbIdioma.setValue(null);
    }

    @Override
    public void refrescar() {
        listaProfesores.getItems().setAll(controller.listarProfesores());
        listaAsignaciones.getItems().setAll(matriculaController.listarAsignaciones());
    }
}
