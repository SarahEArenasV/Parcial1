package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new MainView().getRoot(), 1000, 720);
        stage.setTitle("LenguajeCafetero - Gestión académica");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
