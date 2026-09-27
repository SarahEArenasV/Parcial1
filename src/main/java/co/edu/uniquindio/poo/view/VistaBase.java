package co.edu.uniquindio.poo.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

public abstract class VistaBase {
    private final Tab tab;

    protected VistaBase(String titulo) {
        tab = new Tab(titulo);
        tab.setClosable(false);
    }

    protected void setContenido(Node contenido) {
        ajustarCombos(contenido);
        ScrollPane scroll = new ScrollPane(contenido);
        scroll.setFitToWidth(true);
        tab.setContent(scroll);
    }

    private void ajustarCombos(Node nodo) {
        if (nodo instanceof ComboBox<?> combo) {
            combo.setPrefWidth(260);
        }
        if (nodo instanceof Parent padre) {
            for (Node hijo : padre.getChildrenUnmodifiable()) {
                ajustarCombos(hijo);
            }
        }
    }

    public Tab getTab() {
        return tab;
    }

    public abstract void refrescar();

    protected GridPane crearFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));
        return grid;
    }

    protected Label titulo(String texto) {
        Label label = new Label(texto);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return label;
    }

    protected void mostrarError(String mensaje) {
        mostrar(Alert.AlertType.ERROR, "Error", mensaje);
    }

    protected void mostrarInfo(String mensaje) {
        mostrar(Alert.AlertType.INFORMATION, "Información", mensaje);
    }

    private void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
