package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Product;
import org.example.model.User;
import org.example.services.ProductsService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public Label userLabel;
    @FXML
    public Text registrationMessage;
    @FXML
    public Button backToMainButton;
    @FXML
    public ListView<Product> productsList;
    @FXML
    public Button addProductButton;
    @FXML
    public Button editProductButton;
    @FXML
    public Button deleteProductButton;

    @FXML
    public ImageView selectedProductImg;

    private static User brand;



    private static Product selectedProd;
    private static List<Product> products = new ArrayList<>();

    private void updateProductList() throws Exception {
        productsList.getItems().clear();
        products.removeAll(products);
        List<Product> p = new ArrayList<>();
        p = ProductsService.getProducts(brand.getUsername());
        products = p;
        productsList.getItems().addAll(products);
    }
    public static List<Product> getProducts() {
        return products;
    }
    public void selectedProd(Product prod) throws Exception {
        selectedProd=prod;
        updateProductList();
        updateGUI();

    }

    public void listViewSelectedProduct(){
        if (productsList.getSelectionModel().getSelectedItem()!=null) {
            selectedProd = productsList.getSelectionModel().getSelectedItem();
            updateGUI();
        }
    }
    private void updateGUI()
    {
        selectedProductImg.setImage(new Image(selectedProd.getImageUrl()));
    }
    public ProductsController(){

    }

    public void initData(User br) throws Exception{
        this.brand = br;
        this.userLabel.setText("Hello, " + brand.getUsername() + "!");
        updateProductList();

    }

    public void handleBackToMain() throws IOException {
        User us=ProductsController.getBrand();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("brandmain.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        BrandmainController control = loader.getController();
        control.initData(us);
        Stage stage1 = (Stage) backToMainButton.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

    public void handleAddProductAction() throws  IOException {
        User us=ProductsController.getBrand();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("addProduct.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        AddProductController control = loader.getController();
        control.initialize(us);
        Stage stage1 = (Stage) addProductButton.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

    public void handleEditProductAction() throws Exception {
        User us=ProductsController.getBrand();
        Product p = ProductsController.getSelectedProd();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("editproduct.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        EditProductController control = loader.getController();
        control.initData(us,p);
        Stage stage1 = (Stage) editProductButton.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();

    }

    public static Product getSelectedProd() {
        return selectedProd;
    }

    public void handleDeleteProductAction() throws Exception {
        User us=ProductsController.getBrand();
        Product p = ProductsController.getSelectedProd();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("deleteProduct.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        DeleteProductController control = loader.getController();
        control.initData(us,p);
        Stage stage1 = (Stage) deleteProductButton.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();

    }

    public static User getBrand() {
        return brand;
    }

    public static void setBrand(User brand) {
        ProductsController.brand = brand;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ProductsService.loadProductsFromFile();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        
    }
}
