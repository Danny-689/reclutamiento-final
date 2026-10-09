package main.java.edu.kinal.reclutamiento.controller;

import main.java.edu.kinal.reclutamiento.exception.ValidarException;
import main.java.edu.kinal.reclutamiento.model.Aspirantes;
import main.java.edu.kinal.reclutamiento.service.AspirantesService;
import main.java.edu.kinal.reclutamiento.utils.SceneManager;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AspiranteController {

    @FXML private TableView<Aspirantes> tablaAspirantes;

    @FXML private TableColumn<Aspirantes, Integer> colId;
    @FXML private TableColumn<Aspirantes, String> colNombre;
    @FXML private TableColumn<Aspirantes, String> colApellido;
    @FXML private TableColumn<Aspirantes, String> colCorreo;
    @FXML private TableColumn<Aspirantes, String> colTelefono;
    @FXML private TableColumn<Aspirantes, String> colDireccion;
    @FXML private TableColumn<Aspirantes, Integer> colEdad;

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtEdad;

    private final AspirantesService aspiranteService;
    private final SceneManager sceneManager;

    public AspiranteController(
            AspirantesService aspiranteService,
            SceneManager sceneManager) {
        this.aspiranteService = aspiranteService;
        this.sceneManager = sceneManager;
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(
                new PropertyValueFactory<>("idAspirante"));
        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(
                new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(
                new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(
                new PropertyValueFactory<>("direccion"));
        colEdad.setCellValueFactory(
                new PropertyValueFactory<>("edad"));

        tablaAspirantes.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        mostrarSeleccionado(seleccionado);
                    }
                });

        cargarDatos();
    }

    @FXML
    private void guardar() {
        try {
            Aspirantes a = leerFormulario();
            aspiranteService.guardar(a);

            mostrar("Aspirante guardado correctamente.",
                    Alert.AlertType.INFORMATION);

            limpiar();
            cargarDatos();

        } catch (ValidarException e) {
            mostrar(e.getMessage(), Alert.AlertType.WARNING);
        } catch (NumberFormatException e) {
            mostrar("La edad debe ser un número válido.",
                    Alert.AlertType.WARNING);
        } catch (RuntimeException e) {
            mostrar("No se pudo guardar el aspirante.",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void actualizar() {
        Aspirantes seleccionado =
                tablaAspirantes.getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {
            mostrar("Selecciona un aspirante.",
                    Alert.AlertType.WARNING);
            return;
        }

        try {
            Aspirantes a = leerFormulario();
            a.setIdAspirante(seleccionado.getIdAspirante());

            aspiranteService.actualizar(a);

            mostrar("Aspirante actualizado.",
                    Alert.AlertType.INFORMATION);

            limpiar();
            cargarDatos();

        } catch (ValidarException e) {
            mostrar(e.getMessage(), Alert.AlertType.WARNING);
        } catch (NumberFormatException e) {
            mostrar("La edad debe ser un número válido.",
                    Alert.AlertType.WARNING);
        } catch (RuntimeException e) {
            mostrar("No se pudo actualizar el aspirante.",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminar() {
        Aspirantes seleccionado =
                tablaAspirantes.getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {
            mostrar("Selecciona un aspirante.",
                    Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Deseas eliminar a "
                + seleccionado.getNombre() + "?");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL)
                == ButtonType.OK) {

            try {
                aspiranteService.eliminar(
                        seleccionado.getIdAspirante());

                limpiar();
                cargarDatos();

                mostrar("Aspirante eliminado.",
                        Alert.AlertType.INFORMATION);

            } catch (RuntimeException e) {
                mostrar("No se pudo eliminar el aspirante.",
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtEdad.clear();
        tablaAspirantes.getSelectionModel().clearSelection();
    }

    @FXML
    private void cerrarSesion() {
        try {
            sceneManager.cambiarEscena(
                    "/view/login/login-view.fxml");
        } catch (IOException e) {
            mostrar("No se pudo volver al login.",
                    Alert.AlertType.ERROR);
        }
    }

    private Aspirantes leerFormulario() {
        return new Aspirantes(
                0,
                txtNombre.getText().trim(),
                txtApellido.getText().trim(),
                txtCorreo.getText().trim(),
                txtTelefono.getText().trim(),
                txtDireccion.getText().trim(),
                Integer.parseInt(txtEdad.getText().trim())
        );
    }

    private void mostrarSeleccionado(Aspirantes a) {
        txtNombre.setText(a.getNombre());
        txtApellido.setText(a.getApellido());
        txtCorreo.setText(a.getCorreo());
        txtTelefono.setText(a.getTelefono());
        txtDireccion.setText(a.getDireccion());
        txtEdad.setText(String.valueOf(a.getEdad()));
    }

    private void cargarDatos() {
        try {
            tablaAspirantes.setItems(
                    FXCollections.observableArrayList(
                            aspiranteService.listar()));
        } catch (RuntimeException e) {
            mostrar("No se pudo cargar el inventario.",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrar(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}