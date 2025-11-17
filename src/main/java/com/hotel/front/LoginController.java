package com.hotel.front;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;

    @FXML
    private void loginUser() {
        String usuario = txtUsuario.getText();
        String pass = txtPassword.getText();

        if (usuario == null || usuario.isBlank()
                || pass == null || pass.isBlank()) {
            mostrarAlerta("Datos incompletos", "Tengo que completar usuario y contraseña.");
            return;
        }

        // Más adelante acá voy a conectar con AuthService / JSON / lo que decidan.
        mostrarAlerta("Login", "Simulo un inicio de sesión con el usuario: " + usuario);
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
            e.printStackTrace();
            mostrarAlerta("Error", "No pude volver a la pantalla principal.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
