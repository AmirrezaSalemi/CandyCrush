package com.example.candycrush;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RectangleSwap extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        Rectangle rect1 = new Rectangle(50, 50, 100, 100);
        rect1.setFill(Color.RED);
        rect1.setTranslateX(50);
        rect1.setTranslateY(50);

        Rectangle rect2 = new Rectangle(200, 200, 100, 100);
        rect2.setFill(Color.BLUE);
        rect2.setTranslateX(200);
        rect2.setTranslateY(200);

        root.getChildren().addAll(rect1, rect2);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Create TranslateTransition for rect1
        TranslateTransition tt1 = new TranslateTransition(Duration.seconds(1), rect1);
        tt1.setToX(rect2.getTranslateX());
        tt1.setToY(rect2.getTranslateY());

        // Create TranslateTransition for rect2
        TranslateTransition tt2 = new TranslateTransition(Duration.seconds(1), rect2);
        tt2.setToX(rect1.getTranslateX());
        tt2.setToY(rect1.getTranslateY());

        // Play the animations
        tt1.play();
        tt2.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
