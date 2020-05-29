package org.example;

import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;
        import org.example.services.UserService;

import java.util.Objects;

public class Main extends Application {

    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        UserService.loadUsersFromFile();
        Parent root = (Parent)FXMLLoader.load(Objects.requireNonNull(this.getClass().getClassLoader().getResource("start.fxml")));
        primaryStage.setTitle("MOA");
        primaryStage.setScene(new Scene(root, 525.0D, 448.0D));
        primaryStage.show();

    }
}
