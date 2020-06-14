module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.io;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens org.example to javafx.fxml;
    opens org.example.controllers to javafx.fxml;
    exports org.example;
    exports org.example.model;
    exports org.example.controllers;
}