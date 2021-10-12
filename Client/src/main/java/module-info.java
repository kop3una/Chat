module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens myApp to javafx.fxml;
    exports myApp;
    exports myApp.scene;
    opens myApp.scene to javafx.fxml;
}