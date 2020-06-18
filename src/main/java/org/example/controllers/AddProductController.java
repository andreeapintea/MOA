package org.example.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.exceptions.ProductAlreadyExists;
import org.example.exceptions.UsernameAlreadyExistException;
import org.example.model.User;
import org.example.services.ProductsService;
import org.example.services.UserService;

import javax.imageio.ImageIO;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    ObservableList<String> unitList = FXCollections.observableArrayList("ml", "g");
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public Label userLabel;
    @FXML
    public Text registrationMessage;
    @FXML
    public Button cancelButton;
    @FXML
    public Button addButton;
    @FXML
    public TextField productNameField;
    @FXML
    public TextField quantityField;
    @FXML
    public ChoiceBox unit;
    @FXML
    public TextField priceField;
    @FXML
    public TextField numProd;
    @FXML
    public ImageView productImage;
    @FXML
    public Button selectImageButton;

    @FXML
    public Text addProductMessage;

    @FXML
    public Button resetImageButton;

    private static User brand;
    private String path;

    public AddProductController(){

    }

    public void initialize(User br) {

        this.unit.setValue("ml");
        this.unit.setItems(unitList);
        this.brand = br;
    }

    public void handleResetAction(){
        productImage.setImage(new Image("def_pic.jpg"));
        setPath("def_pic.jpg");
    }

    public void handleSelectImage(){

        Stage stage = (Stage) selectImageButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        //filter for .jpg
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg");
        fileChooser.getExtensionFilters().add(imageFilter);
        File imageFile = fileChooser.showOpenDialog(stage);
        if (imageFile.isFile()){
            //System.out.println(imageFile.getPath());
            setPath(imageFile.toURI().toString());
            productImage.setImage(new Image(imageFile.toURI().toString()));
        }
    }

    public void copyImageFile(){


    }

    public void handleAddAction(){
        if (fieldsReadyToSubmit()) {
            try {
                int quant = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                int number = Integer.parseInt(numProd.getText());
                String imageUrl = getPath();
                ProductsService.addProduct(this.productNameField.getText(), imageUrl, quant, price, (String) this.unit.getValue(),number);
                this.addProductMessage.setText("Product has been successfully added!");
            } catch (ProductAlreadyExists var2) {
                this.addProductMessage.setText(var2.getMessage());
            }
        }

    }
    private boolean fieldsReadyToSubmit()
    {
        if (productNameField.getText() == null || productNameField.getText().trim().isEmpty() || quantityField.getText()==null || quantityField.getText().trim().isEmpty() || priceField.getText() == null || priceField.getText().trim().isEmpty())
            return false;
        return true;
    }

    public void handleCancelAction() throws IOException, Exception {
        User us=AddProductController.getBrand();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("products.fxml"));
        Parent root1 = loader.load();
        Scene scene1 = new Scene(root1);
        ProductsController control = loader.getController();
        //MakeUpBrand br = new MakeUpBrand(us);
        control.initData(us);
        Stage stage1 = (Stage) cancelButton.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();
    }

    public static User getBrand() {
        return brand;
    }

    public static void setBrand(User brand) {
        AddProductController.brand = brand;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)

    {
        try {
            ProductsService.loadProductsFromFile();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        productImage.setImage(new Image("def_pic.jpg"));
        setPath("def_pic.jpg");
        priceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(!newValue.matches("\\d*(\\.\\d*)?")){
                    priceField.setText(oldValue);
                }
            }
        });

        quantityField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(!newValue.matches("\\d*?")){
                    quantityField.setText(oldValue);
                }
            }
        });

        numProd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(!newValue.matches("\\d*?")){
                    numProd.setText(oldValue);
                }
            }
        });
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
