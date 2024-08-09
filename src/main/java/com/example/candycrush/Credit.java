package com.example.candycrush;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.sound.sampled.Clip;

import java.io.FileWriter;
import java.io.IOException;

import static com.example.candycrush.MainMenu.clip;
import static com.example.candycrush.MainMenu.path;

public class Credit extends Application {
    @Override
    public void start(Stage stage){
        Image BackGround = new Image(MainMenu.path.toAbsolutePath() + "\\Wallpaper.png");
        ImageView BG = new ImageView(BackGround);

        DropShadow bright = new DropShadow(15, Color.WHITE);
        bright.setInput(new Glow());

        Image Back = new Image(MainMenu.path.toAbsolutePath() + "\\back.png");
        ImageView back = new ImageView(Back);
        back.setFitHeight(50);
        back.setFitWidth(50);
        back.setOnMouseEntered(mouseEvent -> {
            back.setEffect(bright);
        });
        back.setOnMouseExited(mouseEvent -> {
            back.setEffect(null);
        });
        back.setOnMouseClicked(mouseEvent -> {
            new MainMenu().start(stage);

        });

        Image unmuting = new Image(path.toAbsolutePath() + "\\UnMute.png");
        Image muting = new Image(path.toAbsolutePath() + "\\Mute.png");
        ImageView muteUnmute = new ImageView();
        muteUnmute.setFitHeight(50);
        muteUnmute.setFitWidth(50);
        muteUnmute.setY(540);
        muteUnmute.setX(740);
        muteUnmute.setOnMouseEntered(mouseEvent -> {
            muteUnmute.setEffect(bright);
        });
        muteUnmute.setOnMouseExited(mouseEvent -> {
            muteUnmute.setEffect(null);
        });
        muteUnmute.setOnMouseClicked(mouseEvent -> {
            if(clip.isActive()){
                try {
                    FileWriter fileWriter = new FileWriter(path.toAbsolutePath() + "\\Music.txt");
                    fileWriter.write("");
                    fileWriter.close();
                    fileWriter = new FileWriter(path.toAbsolutePath() + "\\Music.txt", true);
                    fileWriter.write("Mute");
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                clip.stop();
                muteUnmute.setImage(muting);
            } else {
                try {
                    FileWriter fileWriter = new FileWriter(path.toAbsolutePath() + "\\Music.txt");
                    fileWriter.write("");
                    fileWriter.close();
                    fileWriter = new FileWriter(path.toAbsolutePath() + "\\Music.txt", true);
                    fileWriter.write("UnMute");
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                muteUnmute.setImage(unmuting);
            }
        });
        if(clip.isActive()){
            muteUnmute.setImage(unmuting);
        } else {
            muteUnmute.setImage(muting);
        }


        // Create a rectangle
        Rectangle rectangle = new Rectangle(280, 120);
        rectangle.setFill(Color.WHITESMOKE);
        rectangle.setStroke(Color.LIGHTPINK);

        // Create a text
        Text text = new Text("Created with love by Amirreza Salemi \nthanks for spend your time with us💕.");
        text.setFont(Font.font("harrington", 16));

        // Create a StackPane and add rectangle and text to it
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(BG, rectangle, text, back, muteUnmute);

        StackPane.setAlignment(muteUnmute, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(muteUnmute, new Insets(10));
        StackPane.setAlignment(back, Pos.TOP_LEFT);

        // Create a scene and add stackPane to it
        Scene scene = new Scene(stackPane, 800, 600);

        // Set the scene and show the stage
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        clip.loop(0);
        launch();
    }
}
