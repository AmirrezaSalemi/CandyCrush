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

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MainMenu extends Application {
    public static Path path = Paths.get("src");
    static Clip clip = null;
    @Override
    public void start(Stage stage){
        Image icon = new Image(path.toAbsolutePath() + "\\logo.png");
        Image BackGround = new Image(path.toAbsolutePath() + "\\Wallpaper.png");
        ImageView BG = new ImageView(BackGround);
        stage.getIcons().addAll(icon);
        Group group = new Group();
        Scene scene = new Scene(group, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Candy Crush");
        group.getChildren().addAll(BG);

        Image unmuting = new Image(path.toAbsolutePath() + "\\UnMute.png");
        Image muting = new Image(path.toAbsolutePath() + "\\Mute.png");
        ImageView muteUnmute = new ImageView();
        muteUnmute.setFitHeight(50);
        muteUnmute.setFitWidth(50);
        muteUnmute.setY(540);
        muteUnmute.setX(740);
        muteUnmute.setOnMouseEntered(mouseEvent -> {
            DropShadow bright = new DropShadow(10, Color.WHITE);
            bright.setInput(new Glow());
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


        CustomButton exit = new CustomButton("Exit", 200, 200, actionEvent -> {
            stage.close();
        }); // Exit button.
        CustomButton start = new CustomButton("Start", 200, 200, actionEvent -> {
            Login_Signin loginSignin = new Login_Signin();
            loginSignin.start(stage);
        });
        CustomButton scores = new CustomButton("Records", 200, 200, actionEvent -> {
            Records records = new Records();
            records.start(stage);
        });
        CustomButton credit = new CustomButton("Credit", 200, 200, actionEvent -> {
            Credit c = new Credit();
            try {
                c.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        start.setLayoutX(310);
        start.setLayoutY(0);

        scores.setLayoutX(310);
        scores.setLayoutY(125);

        credit.setLayoutX(310);
        credit.setLayoutY(250);

        exit.setLayoutX(310);
        exit.setLayoutY(370);
        group.getChildren().addAll(exit, scores, credit, start, muteUnmute);
        stage.show();
    }

    public static void main(String[] args) {

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

        //Background music
        File audioFile = new File(path.toAbsolutePath() + "\\Background music.wav");
        try {
            clip = AudioSystem.getClip();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioInput);
            if(line.equals("UnMute")) {
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        launch();
    }
}
