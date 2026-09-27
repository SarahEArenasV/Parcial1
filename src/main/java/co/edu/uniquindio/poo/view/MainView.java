package co.edu.uniquindio.poo.view;

import co.edu.uniquindio.poo.controller.AcademiaController;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class MainView {
    private final BorderPane root = new BorderPane();

    public MainView() {
        AcademiaController academiaController = new AcademiaController();
        Label encabezado = new Label(academiaController.obtenerDatosAcademia());
        encabezado.setPadding(new Insets(10));
        encabezado.setStyle("-fx-font-weight: bold; -fx-background-color: #6f4e37; -fx-text-fill: white;");
        encabezado.setMaxWidth(Double.MAX_VALUE);
        encabezado.setWrapText(true);

        List<VistaBase> vistas = List.of(new EstudianteView(), new ProfesorView(), new CursoView(),
                new ServicioView(), new MatriculaView(), new ReporteView());

        TabPane tabPane = new TabPane();
        for (VistaBase vista : vistas) {
            tabPane.getTabs().add(vista.getTab());
        }
        tabPane.getSelectionModel().selectedIndexProperty()
                .addListener((obs, anterior, indice) -> vistas.get(indice.intValue()).refrescar());

        root.setTop(encabezado);
        root.setCenter(tabPane);
    }

    public Parent getRoot() {
        return root;
    }
}
