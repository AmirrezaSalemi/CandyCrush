package com.example.candycrush;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static com.example.candycrush.GameArena.*;
import static com.example.candycrush.MainMenu.path;


public class Play {
    static Candy[][] table = new Candy[6][6];
    Candy selectedCandy = null;
    Candy targetCandy = null;
    int x = 50;
    int y = 75;
    int count = 0;
    boolean healthCheck = false;
    boolean possibleTurn = false;
    EventHandler<MouseEvent> mouseHandler;


    public Play(Group group, Stage stage){
        DropShadow bright = new DropShadow(15, Color.WHITE);
        bright.setInput(new Glow());
        mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (selectedCandy == null){
                    selectedCandy = (Candy) mouseEvent.getSource();
                    selectedCandy.setEffect(bright);
                } else {
                    healthCheck = true;
                    targetCandy = (Candy) mouseEvent.getSource();
                    double x1 = selectedCandy.getLayoutX();
                    double y1 = selectedCandy.getLayoutY();
                    double x2 = targetCandy.getLayoutX();
                    double y2 = targetCandy.getLayoutY();
                    if (x1 == x2 && y1 == y2) {
                        selectedCandy.setEffect(null);
                        selectedCandy = null;
                    } else if ((Math.abs(x1 - x2) == 75 && y1 - y2 == 0) || (Math.abs(y1 - y2) == 75 && x1 - x2 == 0)) {
                        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(1), selectedCandy);
                        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), targetCandy);
                        selectedCandy.setLayoutX(x2);
                        selectedCandy.setLayoutY(y2);
                        targetCandy.setLayoutX(x1);
                        targetCandy.setLayoutY(y1);
                        selectedCandy.setEffect(null);
                        Candy temp = table[(int) ((y1 - 75) / 75)][(int) ((x1 - 50) / 75)];
                        table[(int) ((y1 - 75) / 75)][(int) ((x1 - 50) / 75)] = table[(int) ((y2 - 75) / 75)][(int) ((x2 - 50) / 75)];
                        table[(int) ((y2 - 75) / 75)][(int) ((x2 - 50) / 75)] = temp;
                        rowcheck();
                        columncheck();
                        if (healthCheck == true){
                            File audioFile = new File(path.toAbsolutePath() + "\\wrong swap.wav");
                            try {
                                Clip clip = AudioSystem.getClip();
                                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
                                clip.open(audioInput);
                                clip.start();
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                e.printStackTrace();
                            }
                            selectedCandy.setLayoutX(x1);
                            selectedCandy.setLayoutY(y1);
                            targetCandy.setLayoutX(x2);
                            targetCandy.setLayoutY(y2);
                            temp = table[(int) ((y1 - 75) / 75)][(int) ((x1 - 50) / 75)];
                            table[(int) ((y1 - 75) / 75)][(int) ((x1 - 50) / 75)] = table[(int) ((y2 - 75) / 75)][(int) ((x2 - 50) / 75)];
                            table[(int) ((y2 - 75) / 75)][(int) ((x2 - 50) / 75)] = temp;
                            health --;
                            healthCheck = false;
                        }
                        if (health == 0){
                            new loss().start(stage);
                        }
                        possibleTurn = false;
                        possible_Turn(table);
                        selectedCandy.setEffect(null);
                        selectedCandy = null;
                        targetCandy = null;
                        if(possibleTurn == false){
                            for (int i = 0 ; i < 6 ; i++){
                                for (int j = 0 ; j < 6; j++){
                                    table[i][j] = null;
                                }
                            }
                            File audioFile = new File(path.toAbsolutePath() + "\\respawn.wav");
                            try {
                                Clip clip = AudioSystem.getClip();
                                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
                                clip.open(audioInput);
                                clip.start();
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                e.printStackTrace();
                            }
                            GameArena gameArena = new GameArena();
                            gameArena.start(stage);
                        }
                    }
                }
                for (int i = 0; i < 3; i++) {
                    if (i < health){
                        hearts[i].setImage(full);
                    } else {
                        hearts[i].setImage(empty);
                    }
                }
                scoreText.setText(String.valueOf(score));
            }
        };
        for (int i = 0; i < 6; i++) {
            x = 50;
            for (int j = 0; j < 6; j++) {
                Candy candy = new Candy();
                candy.setLayoutX(x + 7.5);
                candy.setLayoutY(y + 7.5);
                candy.setOnMouseClicked(mouseHandler);
                table[i][j] = candy;
                x += 75;
                group.getChildren().add(table[i][j]);
            }
            y += 75;
        }
        for (int i = 0; i < 3; i++) {
            if (health > i){
                hearts[i].setImage(full);
            } else {
                hearts[i].setImage(empty);
            }
            hearts[i].setFitHeight(45);
            hearts[i].setFitWidth(45);
            hearts[i].setLayoutY(10);
            hearts[i].setLayoutX(635 + i * 50);
            group.getChildren().add(hearts[i]);
        }
    }
    boolean check_positive = false;
    public void rowcheck(){
        Candy[][] remove = new Candy[6][6];
        for (int i = 0; i < 6 ; i++){
            for (int j = 0; j < 5 ; j++){
                if (table[i][j].type == table[i][j+1].type){
                    count++;
                    remove[i][j] = table[i][j];
                }
                else {
                    remove[i][j] = table[i][j];
                    check(remove);
                    count = 0;
                    for (int row = 0 ; row < 6 ; row++){
                        for (int column = 0 ; column < 6 ; column++){
                            remove[row][column] = null;
                        }
                    }
                }
                if (j == 4){
                    remove[i][j+1] = table[i][j+1];
                    check(remove);
                    for (int row = 0 ; row < 6 ; row++){
                        for (int column = 0 ; column < 6 ; column++){
                            remove[row][column] = null;
                        }
                    }
                }
            }
            count = 0;
        }
        if (check_positive == true){
            check_positive = false;
            rowcheck();
            columncheck();
        }
    }
    public void columncheck() {
        Candy[][] remove = new Candy[6][6];
        for (int i = 0; i < 6 ; i++){
            for (int j = 0; j < 5 ; j++){
                if (table[j][i].type == table[j + 1][i].type){
                    count++;
                    remove[j][i] = table[j][i];
                }
                else {
                    remove[j][i] = table[j][i];
                    check(remove);
                    count = 0;
                    for (int row = 0 ; row < 6 ; row++){
                        for (int column = 0 ; column < 6 ; column++){
                            remove[row][column] = null;
                        }
                    }
                }
                if (j == 4){
                    remove[j+1][i] = table[j+1][i];
                    check(remove);
                    for (int row = 0 ; row < 6 ; row++){
                        for (int column = 0 ; column < 6 ; column++){
                            remove[row][column] = null;
                        }
                    }
                }
            }
            count = 0;
        }
        if (check_positive == true){
            check_positive = false;
            rowcheck();
            columncheck();
        }

    }
    public void check(Candy[][] remove) {
        if(count >= 4){
            File audioFile = new File(path.toAbsolutePath() + "\\Candy break.wav");
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
                clip.open(audioInput);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
            check_positive = true;
            score += 30;
            healthCheck = false;
            health++;
            if (health >= 3) {
                health = 3;
            }
            for (int i = 0; i < 6 ; i++) {
                for (int j = 0; j < 6; j++) {
                    if (remove[i][j] != null) {
                        table[i][j] = table[i][j].setType();
                    }
                }
            }
        } else if (count == 3){
            File audioFile = new File(path.toAbsolutePath() + "\\Candy break.wav");
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
                clip.open(audioInput);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
            check_positive = true;
            score += 20;
            healthCheck = false;
            for (int i = 0; i < 6 ; i++){
                for (int j = 0; j < 6 ; j++){
                    if (remove[i][j] != null){
                        table[i][j] = table[i][j].setType();
                        remove[i][j] = null;
                    }
                }
            }
        }
        else if (count == 2){
            File audioFile = new File(path.toAbsolutePath() + "\\Candy break.wav");
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
                clip.open(audioInput);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
            check_positive = true;
            score += 10;
            healthCheck = false;
            for (int i = 0; i < 6 ; i++){
                for (int j = 0; j < 6 ; j++){
                    if (remove[i][j] != null){
                        table[i][j] = table[i][j].setType();
                        remove[i][j] = null;
                    }
                }
            }
        }
        possible_Turn(table);
    }
    public void possible_Turn(Candy[][] table){
        for(int i = 0; i < 6; i++){
            for (int j = 0; j < 6 ; j++){
                if (i-1 >= 0 && i+1 < 6 && j-1 >=0 && table[i][j].type == table[i+1][j].type && table[i-1][j-1].type == table[i][j].type){
                    possibleTurn = true;
                }
                else if (i-1 >= 0 && i+1 < 6 && j+1 < 6 && table[i][j].type == table[i+1][j].type && table[i-1][j+1].type == table[i][j].type){
                    possibleTurn = true;
                }
                else if(i-1 >= 0 && i+1 < 6 && j-1 >=0 && table[i][j].type == table[i-1][j].type && table[i+1][j-1].type == table[i][j].type){
                    possibleTurn = true;
                }
                else if (i-1 >= 0 && i+1 < 6 && j+1 < 6 && table[i][j].type == table[i+1][j].type && table[i-1][j+1].type == table[i][j].type){
                    possibleTurn = true;
                }
                else if(i + 1 < 6 && i + 3 < 6 && table[i][j].type == table[i + 1][j].type && table[i][j].type == table[i+3][j].type){
                    possibleTurn = true;
                }
                else if(i-2 >= 0 && i+1 < 6 && table[i][j].type == table[i+1][j].type && table[i][j].type == table[i-2][j].type){
                    possibleTurn = true;
                }
                else if(i-1 >= 0 && j+1 < 6 && j+2 < 6 && table[i][j].type == table[i][j+2].type && table[i][j].type == table[i-1][j+1].type){
                    possibleTurn = true;
                }
                else if(i+1 < 6 && j+1 < 6 && j+2 < 6 && table[i][j].type == table[i][j+2].type && table[i][j].type == table[i+1][j+1].type){
                    possibleTurn = true;
                }
                else if(j+1 < 6 && j+3 < 6 && table[i][j].type == table[i][j+1].type && table[i][j].type == table[i][j+3].type){
                    possibleTurn = true;
                }
                else if (j-2 >= 0 && j+1 < 6 && table[i][j].type == table[i][j+1].type && table[i][j].type == table[i][j-2].type){
                    possibleTurn = true;
                }
                else if(i-1 >= 0 && j-1 >= 0 && j+1 < 6 && table[i][j].type == table[i][j+1].type && table[i][j].type == table[i-1][j-1].type){
                    possibleTurn = true;
                }
                else if (i+1 < 6 && j-1 >= 0 && j+1 < 6 && table[i][j].type == table[i][j+1].type && table[i][j].type == table[i+1][j-1].type){
                    possibleTurn = true;
                }
                else if (j+1 < 6 && i-1 >= 0 && j+2 < 6 && table[i][j].type == table[i][j+1].type && table[i][j].type == table[i-1][j+2].type){
                    possibleTurn = true;
                }
                else if (j+1 < 6 && i+1 < 6 && j+2 < 6 && table[i][j].type == table[i][j+1].type && table[i][j].type == table[i+1][j+2].type){
                    possibleTurn = true;
                }
            }
        }
    }
}
