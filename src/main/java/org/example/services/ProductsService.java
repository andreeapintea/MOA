package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.example.controllers.BrandmainController;
import org.example.exceptions.*;
import org.example.model.Product;
import org.example.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ProductsService {

    public static List<Product> products;
    public static final Path PRODUCTS_PATH = FileSystemService.getPathToFile(new String[]{"config", "products.json"});

    public ProductsService() {
    }

    public static void loadProductsFromFile() throws IOException {
        if (!Files.exists(PRODUCTS_PATH, new LinkOption[0])) {
            FileUtils.copyURLToFile(ProductsService.class.getClassLoader().getResource("products.json"), PRODUCTS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        products = (List)objectMapper.readValue(PRODUCTS_PATH.toFile(), new TypeReference<List<Product>>() {
        });
    }

    public static void addProduct(String productname, String imgurl, int quantity, double price, String measurement, int numItems) throws ProductAlreadyExists {
        String branduser= BrandmainController.getBrand().getUsername();
        String brandname = BrandmainController.getBrand().getName();
        checkProductDoesNotAlreadyExist(productname,branduser);
        products.add(new Product(brandname, productname, branduser, imgurl, quantity, price, measurement,numItems));
        persistProducts();
    }


    private static void checkProductDoesNotAlreadyExist(String productname, String branduser) throws ProductAlreadyExists {

        Iterator var1 = products.iterator();

        Product product;
        do {
            if (!var1.hasNext()) {
                return;
            }

            product = (Product) var1.next();
        } while (!Objects.equals(productname, product.getProductName()));
        if (Objects.equals(branduser, product.getBrandUsername()))
            throw new ProductAlreadyExists(productname);
    }


    private static void persistProducts() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(PRODUCTS_PATH.toFile(), products);
        } catch (IOException var1) {
            throw new CouldNotWriteProductsException();
        }
    }
    public static List<Product> getProducts(String branduser)throws Exception {
        ProductsService.loadProductsFromFile();
        List<Product> prod = new ArrayList<>();
        for (Product pr : products){
            if (Objects.equals(branduser, pr.getBrandUsername())){
                prod.add(pr);
            }
        }
        return prod;

    }

    public static void updateNumberOfItems (Product pr) throws ProductNotInStock{

        for (Product p:products){
            if(p.equals(pr)){
                if (p.getNoOfItems()!=0){
                    p.setNoOfItems(p.getNoOfItems()-1);
                    ProductsService.persistProducts();
                }
                else throw new ProductNotInStock();
            }
        }
        
    }

    public static void editProduct (Product pr, String name, String ImgURL, int quant, int nr, double price, String measure) {
        for (Product p: products) {
            if (p.equals(pr))
            {
                p.setProductName(name);
                p.setImageUrl(ImgURL);
                p.setQuantity(quant);
                p.setNoOfItems(nr);
                p.setPrice(price);
                p.setMeasurement(measure);

                ProductsService.persistProducts();
            }
        }
    }

    public static void deleteProduct (Product p)
    {

        Iterator<Product> iter = products.iterator();

        while (iter.hasNext()) {
            Product pr = iter.next();

            if (pr.equals(p)) {
                iter.remove();
                ProductsService.persistProducts();
            }

        }


    }

}
