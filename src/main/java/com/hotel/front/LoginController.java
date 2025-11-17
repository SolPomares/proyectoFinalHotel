package com.hotel.front;

import Clases.Usuario;
import Clases.SistemaHotel;
import ManejoJSON.Utilidades;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;

    private SistemaHotel<Usuario> sistema; // sistema cargado desde JSON

    @FXML
    public void initialize() {
        try {
            sistema = new SistemaHotel<>(new ArrayList<>(Utilidades.cargarUsuarios()));
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los usuarios del sistema.");
            e.printStackTrace();
        }
    }

    @FXML
    private void loginUser() {
        String usuario = txtUsuario.getText();
        String pass = txtPassword.getText();

        if (usuario.isBlank() || pass.isBlank()) {
            mostrarAlerta("Datos incompletos", "Completá usuario y contraseña.");
            return;
        }

        Usuario user = sistema.gestionarAcceso(usuario, pass);

        if (user == null) {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos.");
            return;
        }

        // Redirigir según rol
        switch (user.getTipoRol()) {
            case ADMINISTRADOR:
                abrirMenu("/com/hotel/front/AdminMenuView.fxml");
                break;

            case RECEPCIONISTA:
                abrirMenu("/com/hotel/front/RecepcionistaMenuView.fxml");
                break;

            case PASAJERO:
                abrirMenu("/com/hotel/front/PasajeroMenuView.fxml");
                break;
        }
    } // ← CIERRE del método loginUser()

    // ---------------------------------------
    // MÉTODOS AUXILIARES (fuera del switch)
    // ---------------------------------------
    private void abrirMenu(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir el menú.");
            e.printStackTrace();
        }
    }

    @FXML
    private void volverAlInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotel/front/PublicView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarAlerta("Error", "No pude volver a la pantalla principal.");
        }
    }

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}
