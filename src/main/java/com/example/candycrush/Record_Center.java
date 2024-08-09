package com.example.candycrush;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static com.example.candycrush.Records.sortedHashMap;

public class Record_Center extends StackPane {
    public static Path path = Paths.get("src");
    public Record_Center(Stage primaryStage){

        // Create a gridpane for this class.
        GridPane gridPane = new GridPane();
        gridPane.setVgap(2.8);
        gridPane.setHgap(2);

        // Create a handmade Table. :)
        for(int i = 0 ; i < 11; i++){
            if(i == 0){
                Text label = new Text(" USER NAME");
                label.setFill(Color.WHITE);
                label.setFont(Font.font("Harrington", 20));
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.BLUEVIOLET);
                rectangle.setStrokeWidth(2);
                rectangle.setStroke(Color.WHITE);
                rectangle.setHeight(25);
                rectangle.setWidth(150);
                label.setCursor(Cursor.NONE);
                gridPane.add(rectangle, 3, i + 2);
                gridPane.add(label,3,i + 2);

                label = new Text(" SCORE");
                label.setFill(Color.WHITE);
                label.setFont(Font.font("Harrington", 20));
                label.setCursor(Cursor.NONE);
                Rectangle rec = new Rectangle();
                rec.setFill(Color.BLUEVIOLET);
                rec.setStrokeWidth(2);
                rec.setStroke(Color.WHITE);
                rec.setHeight(25);
                rec.setWidth(90);
                gridPane.add(rec, 5, i + 2);
                gridPane.add(label,5,i + 2);
                rec.setCursor(Cursor.NONE);
                rectangle.setCursor(Cursor.NONE);
            }
            else {
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.HOTPINK);
                rectangle.setHeight(25);
                rectangle.setWidth(150);
                rectangle.setStrokeWidth(2);
                rectangle.setStroke(Color.WHITE);
                gridPane.add(rectangle, 3, i + 2);
                Rectangle rec = new Rectangle();
                rec.setFill(Color.HOTPINK);
                rec.setHeight(25);
                rec.setWidth(90);
                rec.setStrokeWidth(2);
                rec.setStroke(Color.WHITE);
                gridPane.add(rec, 5, i + 2);
                if( i == 1){
                    Image cursor= new Image(path.toAbsolutePath() + "\\Gold.png");
                    rec.setCursor(new ImageCursor(cursor));
                    rectangle.setCursor(new ImageCursor(cursor));
                }
                else if( i == 2){
                    Image cursor= new Image(path.toAbsolutePath() + "\\Silver.png");
                    rec.setCursor(new ImageCursor(cursor));
                    rectangle.setCursor(new ImageCursor(cursor));
                }
                else if( i == 3){
                    Image cursor= new Image(path.toAbsolutePath() + "\\Bronze.png");
                    rec.setCursor(new ImageCursor(cursor));
                    rectangle.setCursor(new ImageCursor(cursor));
                }
            }
        }

        // Set Records in a Table.
        int counter = 0;
        for (Map.Entry<String, Integer> entry : sortedHashMap.entrySet()) {
            if (counter < 11){
                Text text = new Text(" " + entry.getKey());
                text.setFill(Color.WHITE);
                text.setFont(Font.font("Harrington", 20));
                gridPane.add(text, 3, counter + 3);
                Text score = new Text(" " + entry.getValue());
                score.setFill(Color.WHITE);
                score.setFont(Font.font("Harrington", 20));
                gridPane.add(score, 5, counter+3);
                if ( counter == 0){
                    text.setFill(Color.GOLD);
                    score.setFill(Color.GOLD);
                    Image cursor= new Image(path.toAbsolutePath() + "\\Gold.png");
                    text.setCursor(new ImageCursor(cursor));
                    score.setCursor(new ImageCursor(cursor));
                }
                else if ( counter == 1){
                    text.setFill(Color.SILVER);
                    score.setFill(Color.SILVER);
                    Image cursor= new Image(path.toAbsolutePath() + "\\Silver.png");
                    text.setCursor(new ImageCursor(cursor));
                    score.setCursor(new ImageCursor(cursor));
                }
                else if ( counter == 2){
                    text.setFill(Color.rgb(205,127,50));
                    score.setFill(Color.rgb(205,127,50));
                    Image cursor= new Image(path.toAbsolutePath() + "\\Bronze.png");
                    text.setCursor(new ImageCursor(cursor));
                    score.setCursor(new ImageCursor(cursor));
                }
            }
            else {
                break;
            }
            counter += 1;
        }
        for (int i = 0 ; i < 10 ; i++) {

        }
        this.getChildren().add(gridPane);
    }
}
