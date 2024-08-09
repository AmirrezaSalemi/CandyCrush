package com.example.candycrush;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.sound.sampled.Clip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static com.example.candycrush.MainMenu.path;
import static com.example.candycrush.MainMenu.clip;

public class Login_Signin extends Application {
    public static HashMap<String, String> userpassword = new HashMap<>();
    public static HashMap<String, Integer> scores = new HashMap<>();
    @Override
    public void start(Stage stage){
        try {
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toAbsolutePath() + "\\Users.txt"));
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                // Split each line by ':'
                String[] parts = line.split(":", 2);

                // Add the key-value pair to the HashMap
                userpassword.put(parts[0], parts[1]);

            }

            bufferedReader = new BufferedReader(new FileReader(path.toAbsolutePath() + "\\Scores.txt"));
            line = null;
            while((line = bufferedReader.readLine()) != null) {
                // Split each line by ':'
                String[] parts = line.split(":", 2);

                // Add the key-value pair to the HashMap
                scores.put(parts[0], Integer.parseInt(parts[1]));

            }
            // Always close files.
            bufferedReader.close();
        }
        catch(IOException ignored) {

        }
        Group group = new Group();
        Scene scene = new Scene(group, 800, 600);
        stage.setScene(scene);

        Image BackGround = new Image(path.toAbsolutePath() + "\\Wallpaper.png");
        ImageView BG = new ImageView(BackGround);
        group.getChildren().add(BG);

        DropShadow bright = new DropShadow(15, Color.WHITE);
        bright.setInput(new Glow());

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
        group.getChildren().add(muteUnmute);

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
        group.getChildren().add(back);

        CustomButton signin = new CustomButton("Sign in", 200, 200, actionEvent -> {
            new Signin().start(stage);
        });
        CustomButton login = new CustomButton("Login", 200, 200, actionEvent -> {
            new Login().start(stage);
        });
        login.setLayoutY(200);
        login.setLayoutX(375);

        signin.setLayoutY(200);
        signin.setLayoutX(225);
        group.getChildren().addAll(signin, login);

        stage.show();
    }

    public static void main(String[] args) {
        MainMenu.clip.loop(0);
        launch();
    }
}
