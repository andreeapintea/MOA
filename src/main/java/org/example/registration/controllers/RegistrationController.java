package org.example.registration.controllers;


import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegistrationController {
    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox role;

    public RegistrationController() {
    }

    @FXML
    public void initialize() {
        this.role.getItems().addAll(new Object[]{"Client", "Admin"});
    }

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(this.usernameField.getText(), this.passwordField.getText(), (String)this.role.getValue());
            this.registrationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException var2) {
            this.registrationMessage.setText(var2.getMessage());
        }

    }
}