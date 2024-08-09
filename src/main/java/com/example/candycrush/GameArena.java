package com.example.candycrush;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.example.candycrush.MainMenu.path;
import static com.example.candycrush.MainMenu.clip;

public class GameArena extends Application {
    static Image empty = new Image(path.toAbsolutePath() + "\\emptyHeart.png");
    static Image full = new Image(path.toAbsolutePath() + "\\fullHeart.png");
    public static ImageView[] hearts = {new ImageView(full), new ImageView(full), new ImageView(full)};
    public static int health = 3;
    public static Text scoreText;
    public static int score = 0;

    @Override
    public void start(Stage stage) {
        Image BackGround = new Image(path.toAbsolutePath() + "\\Game BackGround.png");
        ImageView BG = new ImageView(BackGround);
        BG.setFitWidth(1250);
        BG.setFitHeight(600);
        Group group = new Group();
        Scene scene = new Scene(group, 800, 600);
        stage.setScene(scene);
        group.getChildren().add(BG);

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
            String line = null;
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(path.toAbsolutePath() + "\\Music.txt"));
                line = reader.readLine();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(line.equals("UnMute")) {
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            new MainMenu().start(stage);

        });
        group.getChildren().add(back);

        int x;
        int y = 75;
        for (int i = 0; i < 6; i++) {
            x = 50;
            for (int j = 0; j < 6; j++) {
                Rectangle rectangle= new Rectangle();
                rectangle.setLayoutY(y);
                rectangle.setLayoutX(x);
                rectangle.setWidth(75);
                rectangle.setHeight(75);
                rectangle.setStroke(Color.DARKGRAY);
                rectangle.setStrokeWidth(2.5);
                rectangle.setFill(Color.GRAY);
                group.getChildren().add(rectangle);
                x += 75;
            }
            y += 75;
        }
        Play play = new Play(group, stage);
        play.rowcheck();
        play.columncheck();
        Rectangle rectangle= new Rectangle();
        rectangle.setLayoutY(250);
        rectangle.setLayoutX(550);
        rectangle.setWidth(175);
        rectangle.setHeight(50);
        rectangle.setStroke(Color.DARKGRAY);
        rectangle.setStrokeWidth(2.5);
        rectangle.setFill(Color.GRAY);

        scoreText = new Text();
        scoreText.setText(String.valueOf(score));
        scoreText.setEffect(bright);
        scoreText.setLayoutY(285);
        scoreText.setLayoutX(560);
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("Harrington", 38));

        group.getChildren().addAll(rectangle, scoreText);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
