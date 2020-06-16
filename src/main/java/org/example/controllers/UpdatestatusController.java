package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.exceptions.CannotFindOrder;
import org.example.model.Order;
import org.example.model.User;
import org.example.services.OrdersService;
import org.example.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdatestatusController implements Initializable {
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public Button backButton;
    @FXML
    public Button updateButton;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label statusLabel;
    @FXML
    public ChoiceBox statuses;

    private static Order order;
    private static User brand;
    ObservableList<String> stats = FXCollections.observableArrayList("pending", "processed", "delivered");

    public UpdatestatusController(){

    }

    public static Order getOrder() {
        return order;
    }

    public static void setOrder(Order order) {
        UpdatestatusController.order = order;
    }

    public static User getBrand() {
        return brand;
    }

    public static void setBrand(User brand) {
        UpdatestatusController.brand = brand;
    }

    public void initData(User br, Order or){
        this.order = or;
        this.brand=br;
        this.statuses.setValue("pending");
        this.statuses.setItems(stats);
    }

    public void handleUpdateAction() throws Exception{
        try {
            String newStatus = (String) this.statuses.getValue();
            OrdersService.changeOrderStatus(this.order, newStatus);
            statusLabel.setText("Status was successfully updated");
        }catch (CannotFindOrder var){
            statusLabel.setText("There was an error changing the status");
        }
    }

    public void handleBackAction() throws Exception {
        User us=this.getBrand();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("ordersBrand.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        OrdersBrandController control = loader.getController();
        //MakeUpBrand br = new MakeUpBrand(us);
        control.initData(us);
        Stage stage1 = (Stage) backButton.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            OrdersService.loadOrdersFromFile();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
