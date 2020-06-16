package org.example.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.exceptions.UserDoesNotExist;
import org.example.exceptions.WrongPassword;
import org.example.model.User;
import org.example.services.UserService;

import java.io.IOException;

public class LoginController {

    @FXML
    public AnchorPane rootPane2;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField usernameField;

    @FXML
    public Text loginMessage;

    @FXML
    public Button BacktoLoginButton;

    @FXML
    public Button login;

    public LoginController(){

    }

    public void handleBacktoLoginButton() throws IOException {
        Stage stage = (Stage) rootPane2.getScene().getWindow();
        Stage prevStage = (Stage) BacktoLoginButton.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleLoginAction() throws Exception{
        try {
            User us = UserService.checkLogin(this.usernameField.getText(), this.passwordField.getText());
            if (UserService.checkRole(us)==1){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getClassLoader().getResource("clientmain.fxml"));
                Parent root1 = loader.load();
                Scene scene1 = new Scene(root1);
                ClientmainController control = loader.getController();
                //Client cl = new Client(us);
                control.initData(us);
                Stage stage1 = (Stage) login.getScene().getWindow();
                stage1.setScene(scene1);
                stage1.show();
                //this.loginMessage.setText("Logged in as client");
            }
            if (UserService.checkRole(us)==2){
                /*// = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("brandmain.fxml"));
                AnchorPane pane = FXMLLoader.load(this.getClass().getClassLoader().getResource("brandmain.fxml"));
                //Scene scene = login.getScene();
                rootPane2.getChildren().setAll(pane);
                //this.loginMessage.setText("Logged in as makeupbrand");*/
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getClassLoader().getResource("brandmain.fxml"));
                Parent root1 = loader.load();
                Scene scene1 = new Scene(root1);
                BrandmainController control = loader.getController();
                //MakeUpBrand br = new MakeUpBrand(us);
                control.initData(us);
                Stage stage1 = (Stage) login.getScene().getWindow();
                stage1.setScene(scene1);
                stage1.show();
            }


        } catch (UserDoesNotExist|WrongPassword var3) {
            this.loginMessage.setText(var3.getMessage());
        }
    }
}
