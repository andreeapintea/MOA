package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import org.example.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientmainController implements Initializable {
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public Text userBox;

    private User client;

    public void initData(User c){
        this.client = c;
        this.userBox.setText(client.getUsername());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
