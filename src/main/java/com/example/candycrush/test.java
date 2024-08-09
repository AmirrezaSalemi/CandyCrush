package com.example.candycrush;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class test extends Application {

    private Rectangle rect1;
    private Rectangle rect2;
    private double rect1X;
    private double rect1Y;
    private double rect2X;
    private double rect2Y;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        rect1 = new Rectangle(50, 50, 100, 100);
        rect1.setFill(Color.RED);
        rect1X = rect1.getX();
        rect1Y = rect1.getY();

        rect2 = new Rectangle(200, 200, 100, 100);
        rect2.setFill(Color.BLUE);
        rect2X = rect2.getX();
        rect2Y = rect2.getY();

        rect1.setOnMouseClicked(event -> swapRectangles());
        rect2.setOnMouseClicked(event -> swapRectangles());

        root.getChildren().addAll(rect1, rect2);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("Swap Rectangles");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void swapRectangles() {
        TranslateTransition tt1 = new TranslateTransition(Duration.millis(1000), rect1);
        tt1.setByX(rect2.getX() - rect1.getX());
        tt1.setByY(rect2.getY() - rect1.getY());

        TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000), rect2);
        tt2.setByX(rect1X - rect2X);
        tt2.setByY(rect1Y - rect2Y);

        tt1.play();
        tt2.play();

        tt1.setOnFinished(event -> {
            rect1X = rect2X;
            rect1Y = rect2Y;
        });

        tt2.setOnFinished(event -> {
            rect2X = rect1X;
            rect2Y = rect1Y;
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
