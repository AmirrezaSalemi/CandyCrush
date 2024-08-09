package com.example.candycrush;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.Clip;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static com.example.candycrush.GameArena.health;
import static com.example.candycrush.GameArena.score;
import static com.example.candycrush.Login.player;
import static com.example.candycrush.Login_Signin.scores;
import static com.example.candycrush.MainMenu.clip;
import static com.example.candycrush.MainMenu.path;

public class loss extends Application {
    @Override
    public void start(Stage stage) {
        DropShadow bright = new DropShadow(15, Color.WHITE);
        bright.setInput(new Glow());
        if (scores.get(player) < score){
            scores.put(player, score);
            try {
                FileWriter writer = new FileWriter(path.toAbsolutePath() + "\\Scores.txt");
                for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                    writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
                }
                writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        Group group = new Group();
        Scene scene = new Scene(group, 800, 600);
        stage.setScene(scene);

        Image BackGround = new Image(path.toAbsolutePath() + "\\loss.gif");
        ImageView BG = new ImageView(BackGround);
        BG.setFitHeight(600);
        BG.setFitWidth(800);
        group.getChildren().add(BG);

        Text title = new Text("YOU LOST!");
        title.setFont(Font.font("Harrington", 50));
        title.setFill(Color.RED);
        title.setLayoutY(200);
        title.setLayoutX(300);
        FadeTransition fade = new FadeTransition(Duration.millis(3000), title);
        fade.setFromValue(0);
        fade.setToValue(1);
        group.getChildren().add(title);

        Image pa = new Image(path.toAbsolutePath() + "\\play again.png");
        ImageView playagain = new ImageView(pa);
        playagain.setFitWidth(200);
        playagain.setFitHeight(50);
        playagain.setLayoutY(300);
        playagain.setLayoutX(450);
        Text playagaintext = new Text("Play Again");
        playagaintext.setFill(Color.WHITE);
        playagaintext.setEffect(bright);
        playagaintext.setFont(Font.font("Harrington", 20));
        playagaintext.setLayoutY(332.5);
        playagaintext.setLayoutX(502.5);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), playagain);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        FadeTransition transition = new FadeTransition(Duration.millis(3000), playagaintext);
        transition.setFromValue(0);
        transition.setToValue(1);

        Image qt = new Image(path.toAbsolutePath() + "\\quite.png");
        ImageView quite = new ImageView(qt);
        quite.setFitWidth(200);
        quite.setFitHeight(50);
        quite.setLayoutY(300);
        quite.setLayoutX(225);
        Text quiteText = new Text("Quite");
        quiteText.setFill(Color.WHITE);
        quiteText.setEffect(bright);
        quiteText.setFont(Font.font("Harrington", 20));
        quiteText.setLayoutY(332.5);
        quiteText.setLayoutX(300);
        FadeTransition quitetransition = new FadeTransition(Duration.millis(3000), quite);
        quitetransition.setFromValue(0);
        quitetransition.setToValue(1);
        FadeTransition texttransition = new FadeTransition(Duration.millis(3000), quiteText);
        texttransition.setFromValue(0);
        texttransition.setToValue(1);
        texttransition.play();
        fadeTransition.play();
        quitetransition.play();
        transition.play();
        fade.play();
        playagaintext.setOnMouseClicked(mouseEvent -> {
            health = 3;
            score = 0;
            new GameArena().start(stage);
        });
        playagaintext.setOnMouseExited(mouseEvent -> {
            playagain.setEffect(null);
            playagaintext.setEffect(null);
        });
        playagaintext.setOnMouseEntered(mouseEvent -> {
            playagain.setEffect(bright);
            playagaintext.setEffect(bright);
        });
        playagain.setOnMouseClicked(mouseEvent -> {
            health = 3;
            score = 0;
            new GameArena().start(stage);
        });
        playagain.setOnMouseExited(mouseEvent -> {
            playagain.setEffect(null);
            playagaintext.setEffect(null);
        });
        playagain.setOnMouseEntered(mouseEvent -> {
            playagain.setEffect(bright);
            playagaintext.setEffect(bright);
        });
        quite.setOnMouseEntered(mouseEvent -> {
            quiteText.setEffect(bright);
            quite.setEffect(bright);
        });
        quite.setOnMouseExited(mouseEvent -> {
            quiteText.setEffect(null);
            quite.setEffect(null);
        });
        quite.setOnMouseClicked(mouseEvent -> {
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
        quiteText.setOnMouseEntered(mouseEvent -> {
            quiteText.setEffect(bright);
            quite.setEffect(bright);
        });
        quiteText.setOnMouseExited(mouseEvent -> {
            quiteText.setEffect(null);
            quite.setEffect(null);
        });
        quiteText.setOnMouseClicked(mouseEvent -> {
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
        group.getChildren().addAll(playagain, playagaintext, quite, quiteText);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
