package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.exceptions.ProductNotInStock;
import org.example.exceptions.UsernameAlreadyExistException;
import org.example.model.Product;
import org.example.model.User;
import org.example.services.OrdersService;
import org.example.services.ProductsService;
import org.example.services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BrandProductsController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    public ListView<Product> ProductsList;
    @FXML
    public Text userBox;
    @FXML
    private Button Order;
    @FXML
    private Button BackToBrands;
    @FXML
    private ImageView SelectedProductImage;
    @FXML
    private Text orderMessage;
    private static User client;
    public void initData(User c, User selBrand) throws Exception {
        this.client = c;
        this.selectedBrand = selBrand;
        this.userBox.setText(client.getUsername());
        updateBrandProductsList();
        OrdersService.loadOrdersFromFile();
    }

    public static User getClient() {
        return client;
    }

    public void handleBackBrandsButton() throws Exception {

        User us=BrandProductsController.getClient();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("brands.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        BrandsController control = loader.getController();
        control.initData(us);
        Stage stage1 = (Stage) BackToBrands.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

    public User getSelectedBrand() {
        return selectedBrand;
    }

    private User selectedBrand;

    private static List<Product> brandProducts = new ArrayList<>();

    private void updateBrandProductsList() throws Exception {
        ProductsList.getItems().clear();
        brandProducts.removeAll(brandProducts);
        List<Product> p = new ArrayList<>();
        p = ProductsService.getProducts(selectedBrand.getUsername());
        brandProducts = p;
        ProductsList.getItems().addAll(brandProducts);
    }

    public Product getSelectedProd() {
        return selectedProd;
    }

    private Product selectedProd;

    public void listViewSelectedProduct(){
        selectedProd = ProductsList.getSelectionModel().getSelectedItem();
        updateGUI();
       // System.out.println(selectedProd);
    }
    private void updateGUI()
    {
        SelectedProductImage.setImage(new Image(selectedProd.getImageUrl()));
        this.orderMessage.setText("");
    }

    public void handleOrderAction() throws Exception {
            OrdersService.loadOrdersFromFile();
            if (ProductsList.getSelectionModel().getSelectedItem()!=null)
            {
                try {
                    ProductsService.updateNumberOfItems(this.getSelectedProd());
                    OrdersService.addOrder(this.getClient().getUsername(), this.getSelectedProd());
                    this.orderMessage.setText("You successfully ordered the product");
                } catch (ProductNotInStock var2){
                    this.orderMessage.setText(var2.getMessage());
                }


            }
            updateBrandProductsList();



    }
}
