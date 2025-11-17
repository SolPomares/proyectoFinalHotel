package com.hotel.front;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    @FXML
    private void registrarUsuario() {
        // Acá valido los campos básicos
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();
        String pass = txtPassword.getText();

        if (nombre == null || nombre.isBlank()
                || apellido == null || apellido.isBlank()
                || email == null || email.isBlank()
                || pass == null || pass.isBlank()) {

            mostrarAlerta("Datos incompletos", "Tengo que completar todos los campos para registrarme.");
            return;
        }

        // Por ahora solo muestro un mensajito. Después acá se podría guardar el usuario.
        mostrarAlerta("Registro exitoso", "Registré el usuario: " + nombre + " " + apellido);
    }

    @FXML
    private void volverAlInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotel/front/PublicView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) txtNombre.getScene().getWindow();
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
