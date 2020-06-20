package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import org.example.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
public class ClientmainController implements Initializable {
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public Text userBox;
    @FXML
    private Button viewBrands;
    @FXML
    private Button viewOrders;
    @FXML
    public Button logOut;

    private static User client;

    public void initData(User c) throws Exception{
        this.client = c;
        this.userBox.setText("Hello, " + client.getUsername() + "!");
    }

    public static User getClient() {
        return client;
    }

    public static void setClient(User client) {
        ClientmainController.client = client;
    }

    public void handlelogOutButton() throws IOException {
        Stage stage = (Stage) rootPane2.getScene().getWindow();
        Stage prevStage = (Stage) logOut.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleViewBrandsAction() throws Exception, IOException {
        User us = ClientmainController.getClient();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("brands.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        BrandsController control = loader.getController();
        control.initData(us);
        Stage stage1 = (Stage) viewBrands.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

    public void handleViewOrdersButton() throws Exception {
        User us=ClientmainController.getClient();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("ordersCustomer.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        OrdersCustomerController control = loader.getController();
        control.initData(us);
        Stage stage1 = (Stage) viewOrders.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
