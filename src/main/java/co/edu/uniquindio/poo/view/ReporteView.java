package co.edu.uniquindio.poo.view;

import co.edu.uniquindio.poo.controller.ReporteController;
import co.edu.uniquindio.poo.patronescreacionales.builder.Matricula;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class ReporteView extends VistaBase {
    private final ReporteController controller = new ReporteController();

    private final DatePicker dpInicial = new DatePicker(LocalDate.now().withDayOfYear(1));
    private final DatePicker dpFinal = new DatePicker(LocalDate.now());
    private final Label lblTotal = new Label();
    private final ListView<Matricula> listaMatriculas = new ListView<>();

    public ReporteView() {
        super("Ingresos por periodo");
        construir();
    }

    private void construir() {
        Button btnCalcular = new Button("Calcular ingresos");
        btnCalcular.setOnAction(e -> calcular());
        lblTotal.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox contenido = new VBox(10, titulo("Ingresos generados por matrículas"),
                new HBox(10, new Label("Fecha inicial:"), dpInicial, new Label("Fecha final:"), dpFinal, btnCalcular),
                lblTotal, titulo("Matrículas del periodo"), listaMatriculas);
        contenido.setPadding(new Insets(10));
        setContenido(contenido);
    }

    private void calcular() {
        try {
            confirmarFecha(dpInicial);
            confirmarFecha(dpFinal);
            double total = controller.calcularIngresos(dpInicial.getValue(), dpFinal.getValue());
            lblTotal.setText("Total ingresos: $" + String.format("%,.0f", total));
            listaMatriculas.getItems().setAll(controller.listarMatriculasDelPeriodo(dpInicial.getValue(), dpFinal.getValue()));
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void confirmarFecha(DatePicker picker) {
        String texto = picker.getEditor().getText();
        if (texto == null || texto.isBlank()) {
            picker.setValue(null);
            return;
        }
        try {
            picker.setValue(picker.getConverter().fromString(texto));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Fecha inválida: " + texto);
        }
    }

    @Override
    public void refrescar() {
        lblTotal.setText("");
        listaMatriculas.getItems().clear();
    }
}
