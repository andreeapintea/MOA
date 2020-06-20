package org.example.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.exceptions.UsernameAlreadyExistException;
import org.example.services.UserService;

import java.io.IOException;

public class RegistrationController {

    ObservableList<String> roleList = FXCollections.observableArrayList("Client", "MakeUpBrand");
    @FXML
    public Text registrationMessage;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;
    @FXML
    public ChoiceBox role;
    @FXML
    public Button BacktoLoginButton;
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public TextField nameField;

    public RegistrationController() {
    }

    @FXML
    public void initialize() {
        this.role.setValue("Client");
        this.role.setItems(roleList);
    }

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(this.usernameField.getText(), this.passwordField.getText(), this.nameField.getText(), (String)this.role.getValue());
            this.registrationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExistException var2) {
            this.registrationMessage.setText(var2.getMessage());
        }

    }
    public void handleBacktoLoginButton() throws IOException {
        Stage stage = (Stage) rootPane2.getScene().getWindow();
        Stage prevStage = (Stage) BacktoLoginButton.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}