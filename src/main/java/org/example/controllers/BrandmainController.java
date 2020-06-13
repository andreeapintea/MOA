package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.example.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class BrandmainController implements Initializable {
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public Text userBox;
    @FXML
    private Label usernameLabel;
    private User brand;


    public BrandmainController()
    {

    }
    public void initData(User br){
        this.brand = br;
        this.usernameLabel.setText(brand.getUsername());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
