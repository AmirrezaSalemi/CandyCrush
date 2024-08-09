package com.example.candycrush;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomButton extends StackPane {
    public static Path path = Paths.get("src");
    public CustomButton(String label, int width, int height, EventHandler<ActionEvent> eventHandler){
        Image img = new Image(path.toAbsolutePath() + "\\Button.png");
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setCursor(Cursor.HAND);

        Text text = new Text(label);
        text.setCursor(Cursor.HAND);
        text.setFont(Font.font("Harrington", 22));
        text.setFill(Color.WHITE);

        this.getChildren().addAll(imageView, text);

        DropShadow bright = new DropShadow(15, Color.WHITE);
        bright.setInput(new Glow());

        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(bright);
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                eventHandler.handle(new ActionEvent());
            }
        });

        text.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(bright);
            }
        });
        text.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
        text.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                eventHandler.handle(new ActionEvent());
            }
        });
    }
}
