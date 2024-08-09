package com.example.candycrush;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.sound.sampled.Clip;


import java.io.FileWriter;
import java.io.IOException;

import static com.example.candycrush.Login_Signin.userpassword;
import static com.example.candycrush.MainMenu.clip;
import static com.example.candycrush.MainMenu.path;

public class Signin extends Application {
    @Override
    public void start(Stage stage) {
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
            new Login_Signin().start(stage);

        });
        group.getChildren().add(back);

        Text header = new Text("Sign in");
        header.setFill(Color.WHITE);
        header.setFont(Font.font("Harrington", 32));
        header.setEffect(bright);
        header.setStroke(Color.HOTPINK);
        header.setStrokeWidth(1);
        header.setLayoutX(550);
        header.setLayoutY(140);
        group.getChildren().add(header);

        Text user = new Text("User Name:");
        user.setEffect(bright);
        user.setLayoutY(177.5);
        user.setLayoutX(320);
        user.setFont(Font.font("Harrington", 24));
        user.setStroke(Color.HOTPINK);
        user.setStrokeWidth(1);
        user.setFill(Color.WHITE);
        group.getChildren().add(user);

        TextField userTextField = new TextField();
        userTextField.setEffect(bright);
        userTextField.setFont(Font.font("Harrington", 20));
        userTextField.setMinWidth(300);
        userTextField.setStyle("-fx-control-inner-background: #FF1493;");
        userTextField.setLayoutX(450);
        userTextField.setLayoutY(150);
        group.getChildren().add(userTextField);

        Text pass = new Text("Password:");
        pass.setEffect(bright);
        pass.setLayoutY(225);
        pass.setLayoutX(320);
        pass.setFont(Font.font("Harrington", 24));
        pass.setStroke(Color.HOTPINK);
        pass.setStrokeWidth(1);
        pass.setFill(Color.WHITE);
        group.getChildren().add(pass);

        PasswordField passwordField = new PasswordField();
        passwordField.setEffect(bright);
        passwordField.setFont(Font.font("Harrington", 20));
        passwordField.setMinWidth(300);
        passwordField.setStyle("-fx-control-inner-background: #FF1493;");
        passwordField.setLayoutX(450);
        passwordField.setLayoutY(200);
        group.getChildren().add(passwordField);

        DropShadow wrong = new DropShadow(5, Color.DARKRED);
        wrong.setInput(new Glow());
        Text error = new Text();
        error.setStrokeWidth(1);
        error.setStroke(Color.DARKRED);
        error.setEffect(wrong);
        error.setFont(Font.font("ERROR", 24));
        error.setFill(Color.WHITE);
        error.setLayoutX(472.5);
        error.setLayoutY(260);
        group.getChildren().add(error);
        CustomButton signin = new CustomButton("Sign in", 200, 200, actionEvent -> {
            if (userTextField.getText().length() > 0 && userTextField.getText().length() < 15 && passwordField.getText().length() > 0 && !userpassword.containsKey(userTextField.getText())) {
                try {
                    FileWriter fileWriter = new FileWriter(path.toAbsolutePath() + "\\Users.txt", true);
                    fileWriter.write(userTextField.getText() + ":" + passwordField.getText() + "\n");
                    fileWriter.close();

                    fileWriter = new FileWriter(path.toAbsolutePath() + "\\Scores.txt", true);
                    fileWriter.write(userTextField.getText() + ":" + "0" + "\n");
                    fileWriter.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                new Login_Signin().start(stage);
            } else if (userTextField.getText().length() > 14) {
                error.setText("To Long UserName!");
            } else if (userTextField.getText().length() < 1){
                error.setText("Please fill the UserName box!");
            } else if (userpassword.containsKey(userTextField.getText())){
                error.setText("UserName used by other user!");
            } else if (passwordField.getText().length() < 1){
                error.setText("Please fill the PasswordField!");
            }
        });
        signin.setLayoutX(500);
        signin.setLayoutY(220);
        group.getChildren().add(signin);

        stage.show();
    }

    public static void main(String[] args) {
        MainMenu.clip.loop(0);
        launch();
    }
}
