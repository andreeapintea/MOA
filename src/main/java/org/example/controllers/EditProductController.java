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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.exceptions.ProductAlreadyExists;
import org.example.model.Product;
import org.example.model.User;
import org.example.services.ProductsService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProductController implements Initializable {

    ObservableList<String> unitList = FXCollections.observableArrayList("ml", "g");
    @FXML
    public AnchorPane rootPane2;
    @FXML
    public Label userLabel;
    @FXML
    public Text editMessage;
    @FXML
    public Button cancelButton;
    @FXML
    public Button saveChanges;
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
    public Button changeImgButton;


    public static User getBrand() {
        return brand;
    }

    private static User brand;
    private static Product product;
    private String path;

    public EditProductController()
    {

    }

    public void initData(User br, Product p) throws Exception{
        this.brand = br;
        this.userLabel.setText("Hello, " + brand.getUsername() + "!");
        this.product = p;
        this.quantityField.setText(product.getStringQuantity());
        this.unit.setValue(product.getMeasurement());
        this.numProd.setText(product.getStringNoOfItems());
        this.priceField.setText(product.getStringPrice());
        this.productNameField.setText(product.getProductName());
        productImage.setImage(new Image(product.getImageUrl()));
       // setPath("def_pic.jpg");
        //this.unit.setValue("ml");
        this.unit.setItems(unitList);
    }

    public void handleChangeImgButton()
    {
        Stage stage = (Stage) changeImgButton.getScene().getWindow();
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
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }


    public void handleCancelAction() throws IOException, Exception {
        User us=EditProductController.getBrand();
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

    public void handleSaveChangesAction(){
        if (fieldsReadyToSubmit()) {

                int quant = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                int number = Integer.parseInt(numProd.getText());
                String imageUrl = getPath();
                System.out.println(imageUrl);
                String name = productNameField.getText();
                String measure = (String)unit.getValue();
                ProductsService.editProduct(product,name,imageUrl,quant,number,price,measure);
                this.editMessage.setText("Changes has been successfully saved!");


            }
        }

    private boolean fieldsReadyToSubmit()
    {
        if (productNameField.getText() == null || productNameField.getText().trim().isEmpty() || quantityField.getText()==null || quantityField.getText().trim().isEmpty() || priceField.getText() == null || priceField.getText().trim().isEmpty())
            return false;
        return true;
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
