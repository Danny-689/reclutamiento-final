
package main.java.edu.kinal.reclutamiento;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import main.java.edu.kinal.reclutamiento.utils.SceneManager;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Sistema de Reclutamiento");
        primaryStage.setResizable(false);

        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.cambiarEscena("/main/resources/view/login-view.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}