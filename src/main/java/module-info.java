module com.example.candycrush {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.candycrush to javafx.fxml;
    exports com.example.candycrush;
}