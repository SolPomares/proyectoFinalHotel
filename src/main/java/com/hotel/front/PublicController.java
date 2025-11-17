package com.hotel.front;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PublicController {

    @FXML
    private BorderPane mainPane; // Esto me da acceso a la ventana real

    // Método genérico para cambiar de pantalla
    private void cambiarEscena(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            // Obtengo el stage desde el pane ya mostrado
            Stage stage = (Stage) mainPane.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRegister() {
        cambiarEscena("/com/hotel/front/RegisterView.fxml");
    }

    @FXML
    private void goToLogin() {
        cambiarEscena("/com/hotel/front/LoginView.fxml");
    }

    @FXML
    private void initialize() {
        // Si en el futuro quiero inicializar algo
    }
}
