package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StartController {
    @FXML
    private Button LogIn;

    @FXML
    private Button CreateAccount;

    @FXML
    private AnchorPane rootPane;

    public StartController() {

    }

    public void handelCreateAccount () throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("register.fxml"));
        Scene scene = CreateAccount.getScene();
        rootPane.getChildren().add(root);

    }

    public void  handleLogin() throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("login.fxml"));
        Scene scene = LogIn.getScene();
        rootPane.getChildren().add(root);
    }
}
