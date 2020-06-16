package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Order;
import org.example.model.Product;
import org.example.model.User;
import org.example.services.OrdersService;
import org.example.services.ProductsService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrdersBrandController implements Initializable {
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public Label userLabel;
    @FXML
    public Text registrationMessage;
    @FXML
    public Button backToMainButton;
    @FXML
    public ListView<Order> ordersList;
    @FXML
    public Button updateStatus;

    private static User brand;
    private Order selectedOrder;
    private static List<Order> orders = new ArrayList<>();

    public static User getBrand() {
        return brand;
    }

    public static void setBrand(User brand) {
        OrdersBrandController.brand = brand;
    }

    public void handleBackToMain() throws IOException {
        User us=OrdersBrandController.getBrand();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("brandmain.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        BrandmainController control = loader.getController();
        //MakeUpBrand br = new MakeUpBrand(us);
        control.initData(us);
        Stage stage1 = (Stage) backToMainButton.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }
    private void updateOrderList() throws Exception {
        ordersList.getItems().clear();
        orders.removeAll(orders);
        List<Order> o = new ArrayList<>();
        o = OrdersService.getBrandOrders(brand.getUsername());
        orders = o;
        ordersList.getItems().addAll(orders);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            OrdersService.loadOrdersFromFile();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

    public void initData(User br) throws Exception {
        this.brand = br;
        this.userLabel.setText("Hello, " + brand.getUsername() + "!");
        updateOrderList();
    }
}
