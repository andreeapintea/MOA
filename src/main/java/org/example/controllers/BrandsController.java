package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Product;
import org.example.model.User;
import org.example.services.ProductsService;
import org.example.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BrandsController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    public ListView<User> BrandsList;
    @FXML
    public Text userBox;
    @FXML
    private Button BackToClientMain;
    @FXML
    private Button Select;

    private static List<User> brands = new ArrayList<>();
    private static User client;
    private User selectedBrand;

    public User getSelectedBrand() {
        return selectedBrand;
    }


   public void initData(User c) throws Exception {
        this.client = c;
        this.userBox.setText("Hello, " + client.getUsername() + "!");
         updateBrandsList();
    }

    public static User getClient() {
        return client;
    }

    private void updateBrandsList() throws Exception {
        brands.removeAll(brands);
        List<User> b = new ArrayList<>();
        b = UserService.getBrands();
        brands = b;
        BrandsList.getItems().addAll(brands);
    }

    public void handleBackToClientMainButton() throws Exception {

        User us=BrandsController.getClient();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("clientmain.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        ClientmainController control = loader.getController();
        control.initData(us);
        Stage stage1 = (Stage) BackToClientMain.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

    public void handleSelectButton() throws Exception {

        User us=BrandsController.getClient();
        User br= BrandsList.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("brandProducts.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        BrandProductsController control = loader.getController();
        control.initData(us,br);
        Stage stage1 = (Stage) Select.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            UserService.loadUsersFromFile();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }


}
