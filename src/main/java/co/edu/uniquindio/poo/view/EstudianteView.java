package co.edu.uniquindio.poo.view;

import co.edu.uniquindio.poo.controller.EstudianteController;
import co.edu.uniquindio.poo.model.Estudiante;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class EstudianteView extends VistaBase {
    private final EstudianteController controller = new EstudianteController();

    private final TextField txtNombre = new TextField();
    private final TextField txtDocumento = new TextField();
    private final TextField txtTelefono = new TextField();
    private final TextField txtCorreo = new TextField();
    private final TextField txtEdad = new TextField();
    private final TextField txtBuscar = new TextField();
    private final Label lblResultado = new Label();
    private final ListView<Estudiante> listaEstudiantes = new ListView<>();

    public EstudianteView() {
        super("Estudiantes");
        construir();
        refrescar();
    }

    private void construir() {
        GridPane form = crearFormulario();
        form.addRow(0, new Label("Nombre completo:"), txtNombre);
        form.addRow(1, new Label("Documento:"), txtDocumento);
        form.addRow(2, new Label("Teléfono:"), txtTelefono);
        form.addRow(3, new Label("Correo:"), txtCorreo);
        form.addRow(4, new Label("Edad:"), txtEdad);

        Button btnRegistrar = new Button("Registrar estudiante");
        btnRegistrar.setOnAction(e -> registrar());
        Button btnActualizar = new Button("Actualizar seleccionado");
        btnActualizar.setOnAction(e -> actualizar());
        Button btnEliminar = new Button("Eliminar seleccionado");
        btnEliminar.setOnAction(e -> eliminar());
        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setOnAction(e -> limpiar());
        form.add(new HBox(10, btnRegistrar, btnActualizar, btnEliminar, btnLimpiar), 1, 5);

        listaEstudiantes.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, estudiante) -> cargarEnFormulario(estudiante));

        txtBuscar.setPromptText("Documento de identidad");
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setOnAction(e -> buscar());
        HBox buscarBox = new HBox(10, new Label("Buscar por documento:"), txtBuscar, btnBuscar);

        VBox contenido = new VBox(10, titulo("Registro de estudiantes"), form,
                titulo("Consulta de estudiante"), buscarBox, lblResultado,
                titulo("Estudiantes registrados"), listaEstudiantes);
        contenido.setPadding(new Insets(10));
        setContenido(contenido);
    }

    private void registrar() {
        try {
            Estudiante estudiante = controller.registrarEstudiante(txtNombre.getText(), txtDocumento.getText(),
                    txtTelefono.getText(), txtCorreo.getText(), txtEdad.getText());
            mostrarInfo("Estudiante registrado: " + estudiante.getNombreCompleto());
            limpiar();
            refrescar();
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void cargarEnFormulario(Estudiante estudiante) {
        if (estudiante == null) {
            return;
        }
        txtNombre.setText(estudiante.getNombreCompleto());
        txtDocumento.setText(estudiante.getDocumento());
        txtTelefono.setText(estudiante.getTelefono());
        txtCorreo.setText(estudiante.getCorreo());
        txtEdad.setText(String.valueOf(estudiante.getEdad()));
    }

    private void actualizar() {
        Estudiante seleccionado = listaEstudiantes.getSelectionModel().getSelectedItem();
        try {
            controller.actualizarEstudiante(seleccionado, txtNombre.getText(), txtTelefono.getText(),
                    txtCorreo.getText(), txtEdad.getText());
            mostrarInfo("Estudiante actualizado. El documento no se puede modificar.");
            refrescar();
            limpiar();
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void eliminar() {
        Estudiante seleccionado = listaEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione un estudiante de la lista");
            return;
        }
        try {
            controller.eliminarEstudiante(seleccionado.getDocumento());
            refrescar();
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void buscar() {
        try {
            Optional<Estudiante> encontrado = controller.buscarEstudiante(txtBuscar.getText());
            if (encontrado.isPresent()) {
                Estudiante e = encontrado.get();
                lblResultado.setText("Nombre: " + e.getNombreCompleto() + " | Documento: " + e.getDocumento()
                        + " | Tel: " + e.getTelefono() + " | Correo: " + e.getCorreo()
                        + " | Edad: " + e.getEdad() + " | Registro: " + e.getFechaRegistro());
                listaEstudiantes.getSelectionModel().select(e);
            } else {
                lblResultado.setText("No existe un estudiante con ese documento");
            }
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void limpiar() {
        listaEstudiantes.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtDocumento.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtEdad.clear();
    }

    @Override
    public void refrescar() {
        listaEstudiantes.getItems().setAll(controller.listarEstudiantes());
    }
}
