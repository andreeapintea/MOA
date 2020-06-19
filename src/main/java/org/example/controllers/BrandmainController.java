package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BrandmainController implements Initializable {
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public Text userBox;
    @FXML
    private Label usernameLabel;

    @FXML
    public Button logoutButton;

    @FXML
    public Button viewProductsButton;

    @FXML
    public Button viewOrdersButton;
    private static User brand;


    public BrandmainController()
    {

    }
    public void initData(User br){
        this.brand = br;
        this.usernameLabel.setText("Hello, " + brand.getUsername() + "!");
    }

    public static User getBrand() {
        return brand;
    }

    public static void setBrand(User brand) {
        BrandmainController.brand = brand;
    }

    public void handleLogOutAction() throws IOException {
        Stage stage = (Stage) rootPane2.getScene().getWindow();
        Stage prevStage = (Stage) logoutButton.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleViewProductsAction() throws Exception
    {
        User us=BrandmainController.getBrand();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("products.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        ProductsController control = loader.getController();
        control.initData(us);
        Stage stage1 = (Stage) viewProductsButton.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

    public void handleViewOrdersAction() throws Exception{
        User us=BrandmainController.getBrand();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("ordersBrand.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        OrdersBrandController control = loader.getController();
        control.initData(us);
        Stage stage1 = (Stage) viewProductsButton.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
