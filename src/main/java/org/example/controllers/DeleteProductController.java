package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Product;
import javafx.scene.control.Button;
import org.example.model.User;
import org.example.services.ProductsService;



public class DeleteProductController {

    @FXML
    public AnchorPane root;
    @FXML
    public Text text;

   @FXML
   public Button yesButton;
    @FXML
    public Button back;
    @FXML
    public Button noButton;

    public DeleteProductController(){

    }
    private static User brand;
    private static Product product;

    public static User getBrand() {
        return brand;
    }

    public void initData(User us, Product p)
    {
        this.brand = us;
        this.text.setText("Hello, " + brand.getUsername() + "!");
        this.product = p;
    }

    public void handleYesButton() throws Exception {
        ProductsService.deleteProduct(product);

    }

    public void handleNoButton() throws Exception {
        User us=DeleteProductController.getBrand();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("products.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        ProductsController control = loader.getController();
        control.initData(us);
        Stage stage1 = (Stage) noButton.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

    public void handleBackButton() throws Exception {
        User us=DeleteProductController.getBrand();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("products.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        ProductsController control = loader.getController();
        control.initData(us);
        Stage stage1 = (Stage) back.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

}
