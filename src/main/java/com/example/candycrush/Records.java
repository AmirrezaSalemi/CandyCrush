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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.example.candycrush.Login_Signin.scores;
import static com.example.candycrush.MainMenu.clip;

public class Records extends Application {
    public static Path path = Paths.get("src");
    public static LinkedHashMap<String, Integer> sortedHashMap = new LinkedHashMap<>();
    @Override
    public void start(Stage stage) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toAbsolutePath() + "\\Scores.txt"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                // Split each line by ':'
                String[] parts = line.split(":", 2);

                // Add the key-value pair to the HashMap
                scores.put(parts[0], Integer.parseInt(parts[1]));
            }
            bufferedReader.close();
        } catch (Exception ignored){

        }

        // Convert HashMap into List of Map.Entry
        List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        sortedHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            Records.sortedHashMap.put(entry.getKey(), entry.getValue());
        }

        Image BackGround = new Image(path.toAbsolutePath() + "\\Wallpaper.png");
        ImageView BG = new ImageView(BackGround);
        Group group = new Group();
        Scene scene = new Scene(group, 800, 600);
        group.getChildren().add(BG);
        stage.setScene(scene);

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

        Record_Center recordCenter = new Record_Center(stage);
        recordCenter.setLayoutX(250);
        recordCenter.setLayoutY(100);
        group.getChildren().add(recordCenter);
        stage.show();
    }

    public static void main(String[] args) {
        clip.loop(0);
        launch();
    }
}
