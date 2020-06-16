package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Order;
import org.example.model.User;
import org.example.services.OrdersService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrdersCustomerController implements Initializable {
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public Label userLabel;
    @FXML
    public Button backToMainButton;
    @FXML
    public ListView ordersList;

    private static List<Order> orders = new ArrayList<>();

    private static User customer;
    public OrdersCustomerController(){

    }

    public static User getCustomer() {
        return customer;
    }

    public static void setCustomer(User customer) {
        OrdersCustomerController.customer = customer;
    }

    public void handleBackToMain() throws Exception {
        User us=OrdersCustomerController.getCustomer();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("clientmain.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        ClientmainController control = loader.getController();
        //MakeUpBrand br = new MakeUpBrand(us);
        control.initData(us);
        Stage stage1 = (Stage) backToMainButton.getScene().getWindow();
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
    private void updateOrderList() throws Exception {
        ordersList.getItems().clear();
        orders.removeAll(orders);
        List<Order> o = new ArrayList<>();
        o = OrdersService.getCustomerOrders(customer.getUsername());
        orders = o;
        ordersList.getItems().addAll(orders);
    }

    public void initData(User c) throws Exception {
        this.customer = c;
        this.userLabel.setText("Hello, " + customer.getUsername() + "!");
        updateOrderList();
    }
}
