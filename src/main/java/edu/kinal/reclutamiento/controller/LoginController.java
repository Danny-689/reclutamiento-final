package main.java.edu.kinal.reclutamiento.controller;

import main.java.edu.kinal.reclutamiento.dto.LoginDto;
import main.java.edu.kinal.reclutamiento.exception.ValidarException;
import main.java.edu.kinal.reclutamiento.model.Usuarios;
import main.java.edu.kinal.reclutamiento.service.UsuarioService;
import main.java.edu.kinal.reclutamiento.utils.SceneManager;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;

    private final UsuarioService usuarioService;
    private final SceneManager sceneManager;

    public LoginController(
            UsuarioService usuarioService,
            SceneManager sceneManager) {
        this.usuarioService = usuarioService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void ingresar() {
        try {
            LoginDto dto = new LoginDto(
                    txtCorreo.getText().trim(),
                    txtPassword.getText());

            Usuarios usuario = usuarioService.iniciarSesion(dto);

            if (usuario == null) {
                mostrar("Correo o contraseña incorrectos.",
                        Alert.AlertType.ERROR);
                return;
            }

            sceneManager.cambiarEscena(
                    "/view/aspirantes/aspirantes-view.fxml");

        } catch (ValidarException e) {
            mostrar(e.getMessage(), Alert.AlertType.WARNING);
        } catch (IOException e) {
            mostrar("No se pudo abrir el inventario.",
                    Alert.AlertType.ERROR);
        } catch (RuntimeException e) {
            mostrar("Error al iniciar sesión.",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirRegistro() {
        try {
            sceneManager.cambiarEscena(
                    "/view/registro/registro-view.fxml");
        } catch (IOException e) {
            mostrar("No se pudo abrir el registro.",
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