package org.visualobjectsoftware.intellijaassignment.utility;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Toast {
    public static void show(Stage ownerStage, String message) {
        Label toastLabel = new Label(message);
        toastLabel.setStyle("-fx-background-color: #07072f; -fx-text-fill: #e3a806; -fx-padding: 10px;");

        StackPane stackPane = new StackPane(toastLabel);
        // Set the background of the stack pane to blue instead of transparent
        stackPane.setStyle("-fx-background-color: #07072f; -fx-background-radius: 5px;");
        stackPane.setMinWidth(200);

        Scene scene = new Scene(stackPane);
        scene.setFill(null); // This makes the scene background transparent

        Stage toastStage = new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.initStyle(StageStyle.TRANSPARENT); // Use TRANSPARENT instead of UNDECORATED
        toastStage.setScene(scene);

        toastStage.setX(ownerStage.getX() + ownerStage.getWidth() / 2 - 100);
        toastStage.setY(ownerStage.getY() + ownerStage.getHeight() - 50);

        // Add fade-in animation
        Timeline fadeInTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1))
        );
        fadeInTimeline.play();

        toastStage.show();

        // Add fade-out animation
        Timeline fadeOutTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    toastStage.close();
                }, new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0))
        );
        fadeOutTimeline.setDelay(Duration.seconds(2)); // Delay before fade-out
        fadeOutTimeline.play();
    }
}
