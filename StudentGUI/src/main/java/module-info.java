module com.example.studentgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studentgui to javafx.fxml;
    exports com.example.studentgui;
}