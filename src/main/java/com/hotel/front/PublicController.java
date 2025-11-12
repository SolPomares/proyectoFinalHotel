package com.hotel.front;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class PublicController {

    @FXML private StackPane heroPane;      // fx:id="heroPane"
    @FXML private ImageView carouselImage; // fx:id="carouselImage"

    private final List<Image> images = new ArrayList<>();
    private int index = 0;
    private Timeline rotator;

    private static final int SECONDS_PER_IMAGE = 6;
    private static final double FADE_MS = 700;

    @FXML
    private void initialize() {
        addImage("/com/hotel/front/img/fondo1.jpg");
        addImage("/com/hotel/front/img/fondo2.jpg");


        if (images.isEmpty()) return;

        // Imagen siempre dentro del área, centrada y sin recortes
        carouselImage.setPreserveRatio(true);
        carouselImage.setSmooth(true);
        carouselImage.setPickOnBounds(true);

        // Ajuste automático al tamaño del heroPane
        carouselImage.fitWidthProperty().bind(heroPane.widthProperty());
        carouselImage.fitHeightProperty().bind(heroPane.heightProperty());

        // Centrar la imagen dentro del StackPane
        StackPane.setAlignment(carouselImage, javafx.geometry.Pos.CENTER);

        // Primera imagen
        carouselImage.setImage(images.get(index));

        // Rotación con fade
        rotator = new Timeline(new KeyFrame(Duration.seconds(SECONDS_PER_IMAGE), e -> switchImage()));
        rotator.setCycleCount(Timeline.INDEFINITE);
        rotator.play();
    }

    private void switchImage() {
        FadeTransition out = new FadeTransition(Duration.millis(FADE_MS), carouselImage);
        out.setFromValue(1.0);
        out.setToValue(0.0);
        out.setOnFinished(e -> {
            index = (index + 1) % images.size();
            carouselImage.setImage(images.get(index));

            FadeTransition in = new FadeTransition(Duration.millis(FADE_MS), carouselImage);
            in.setFromValue(0.0);
            in.setToValue(1.0);
            in.play();
        });
        out.play();
    }

    private void addImage(String resourcePath) {
        var url = getClass().getResource(resourcePath);
        if (url != null) {
            images.add(new Image(url.toExternalForm()));
        } else {
            System.err.println("⚠ No se encontró la imagen: " + resourcePath);
        }
    }
}
