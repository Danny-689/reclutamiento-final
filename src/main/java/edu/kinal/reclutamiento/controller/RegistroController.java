package main.java.edu.kinal.reclutamiento.controller;

import main.java.edu.kinal.reclutamiento.exception.ValidarException;
import main.java.edu.kinal.reclutamiento.model.Usuarios;
import main.java.edu.kinal.reclutamiento.service.UsuarioService;
import main.java.edu.kinal.reclutamiento.utils.SceneManager;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;

    private final UsuarioService usuarioService;
    private final SceneManager sceneManager;

    public RegistroController(
            UsuarioService usuarioService,
            SceneManager sceneManager) {
        this.usuarioService = usuarioService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void registrar() {
        try {
            Usuarios usuario = new Usuarios(
                    0,
                    txtNombre.getText().trim(),
                   txtApellido.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtPassword.getText(),
                    1);

            usuarioService.registrar(usuario);

            mostrar("Usuario registrado correctamente.",
                    Alert.AlertType.INFORMATION);

            sceneManager.cambiarEscena(
                    "/view/login/login-view.fxml");

        } catch (ValidarException e) {
            mostrar(e.getMessage(), Alert.AlertType.WARNING);
        } catch (IOException e) {
            mostrar("Usuario registrado, pero no se pudo abrir el login.",
                    Alert.AlertType.WARNING);
        } catch (RuntimeException e) {
            mostrar("No se pudo registrar el usuario.",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void volverLogin() {
        try {
            sceneManager.cambiarEscena(
                    "/view/login/login-view.fxml");
        } catch (IOException e) {
            mostrar("No se pudo abrir el login.",
                    Alert.AlertType.ERROR);
        }
    }

    private void mostrar(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}