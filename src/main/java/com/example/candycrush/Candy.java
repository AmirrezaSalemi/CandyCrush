package com.example.candycrush;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import static com.example.candycrush.MainMenu.path;

public class Candy extends Rectangle {
    public enum CandyType {
        RED, GREEN, BLUE, YELLOW, ORANGE, PURPLE;
        private static final Random PRNG = new Random();

        public static CandyType randomType() {
            CandyType[] types = values();
            return types[PRNG.nextInt(types.length)];
        }

    }

    public CandyType type;

    public static Image getImage(CandyType type) {
        switch (type) {
            case RED -> {
                return new Image(path.toAbsolutePath() + "\\red.png");
            }
            case GREEN -> {
                return new Image(path.toAbsolutePath() + "\\green.png");
            }
            case BLUE -> {
                return new Image(path.toAbsolutePath() + "\\blue.png");
            }
            case YELLOW -> {
                return new Image(path.toAbsolutePath() + "\\yellow.png");
            }
            case ORANGE -> {
                return new Image(path.toAbsolutePath() + "\\orange.png");
            }
            case PURPLE -> {
                return new Image(path.toAbsolutePath() + "\\purple.png");
            }
        }
        return null;
    }


    public Candy() {
        setWidth(60);
        setHeight(60);
        this.type = CandyType.randomType();
        setFill(new ImagePattern(getImage(this.type)));
    }

    public Candy setType() {
        this.type = CandyType.randomType();
        setFill(new ImagePattern(getImage(this.type)));
        return this;
    }
}