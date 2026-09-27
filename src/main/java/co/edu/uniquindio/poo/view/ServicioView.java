package co.edu.uniquindio.poo.view;

import co.edu.uniquindio.poo.controller.ServicioController;
import co.edu.uniquindio.poo.enums.TipoServicio;
import co.edu.uniquindio.poo.model.ServicioAdicional;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ServicioView extends VistaBase {
    private final ServicioController controller = new ServicioController();

    private final ComboBox<TipoServicio> cbTipo = new ComboBox<>();
    private final TextField txtCodigo = new TextField();
    private final TextField txtPrecio = new TextField();
    private final TextField txtDescripcion = new TextField();
    private final ListView<ServicioAdicional> listaServicios = new ListView<>();

    public ServicioView() {
        super("Servicios adicionales");
        construir();
        refrescar();
    }

    private void construir() {
        cbTipo.getItems().setAll(TipoServicio.values());

        GridPane form = crearFormulario();
        form.addRow(0, new Label("Tipo de servicio:"), cbTipo);
        form.addRow(1, new Label("Código:"), txtCodigo);
        form.addRow(2, new Label("Precio:"), txtPrecio);
        form.addRow(3, new Label("Descripción:"), txtDescripcion);
        Button btnCrear = new Button("Registrar servicio");
        btnCrear.setOnAction(e -> crear());
        form.add(btnCrear, 1, 4);

        Button btnDisponible = new Button("Marcar disponible");
        btnDisponible.setOnAction(e -> cambiarDisponibilidad(true));
        Button btnNoDisponible = new Button("Marcar no disponible");
        btnNoDisponible.setOnAction(e -> cambiarDisponibilidad(false));

        VBox contenido = new VBox(10, titulo("Registro de servicios adicionales"), form,
                titulo("Servicios registrados"), listaServicios, new HBox(10, btnDisponible, btnNoDisponible));
        contenido.setPadding(new Insets(10));
        setContenido(contenido);
    }

    private void crear() {
        try {
            ServicioAdicional servicio = controller.crearServicio(cbTipo.getValue(), txtCodigo.getText(),
                    txtPrecio.getText(), txtDescripcion.getText());
            mostrarInfo("Servicio registrado: " + servicio.getNombre());
            txtCodigo.clear();
            txtPrecio.clear();
            txtDescripcion.clear();
            refrescar();
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void cambiarDisponibilidad(boolean disponible) {
        try {
            controller.cambiarDisponibilidad(listaServicios.getSelectionModel().getSelectedItem(), disponible);
            refrescar();
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    @Override
    public void refrescar() {
        listaServicios.getItems().setAll(controller.listarServicios());
    }
}
