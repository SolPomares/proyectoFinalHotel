package com.hotel.front;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminMenuController {


    @FXML
    private void volverAlLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotel/front/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) root).getScene().getWindow();

            stage.setScene(new Scene(root, 600, 400));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
