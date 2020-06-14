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

    private static List<Product> products;
    private static final Path PRODUCTS_PATH = FileSystemService.getPathToFile(new String[]{"config", "products.json"});

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

    public static void addProduct(String productname, String imgurl, int quantity, double price, String measurement) throws ProductAlreadyExists {
        String branduser= BrandmainController.getBrand().getUsername();
        String brandname = BrandmainController.getBrand().getName();
        checkProductDoesNotAlreadyExist(productname,branduser);
        products.add(new Product(brandname, productname, branduser, imgurl, quantity, price, measurement));
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
        /*Iterator var1 = products.iterator();

        Product product;
        do {
            if (!var1.hasNext()) {
                throw new BrandDoesNotExist(branduser);
            }
            prod.add((Product) var1.next());
            product = (Product) var1.next();
        } while (!Objects.equals(branduser, product.getBrandUsername()));
        return prod;*/
    }


    /*private static List<Product> products;
    private static final Path PRODUCTS_PATH = FileSystemService.getPathToFile(new String[]{"config", "products.json"});

    public ProductsService() {
    }

    public static void loadProductsFromFile() throws IOException {
        if (!Files.exists(PRODUCTS_PATH, new LinkOption[0])) {
            FileUtils.copyURLToFile(ProductsService.class.getClassLoader().getResource("products.json"), PRODUCTS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        products = (List) objectMapper.readValue(PRODUCTS_PATH.toFile(), new TypeReference<List<Product>>() {
        });
    }

    public static void addProduct(String productname, String imgurl, int quantity, double price, String measurement) throws ProductAlreadyExists {
        String branduser= BrandmainController.getBrand().getUsername();
        checkProductDoesNotAlreadyExist(productname,branduser);
        products.add(new Product(BrandmainController.getBrand().getName(), productname, branduser, imgurl, quantity, price, measurement));
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
        } while ((!Objects.equals(productname, product.getProductName()))&&(!Objects.equals(branduser, product.getBrandUsername())));

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

    /*public static User getUser(String username) throws Exception {
        UserService.loadUsersFromFile();
        Iterator var1 = users.iterator();

        User user;
        do {
            if (!var1.hasNext()) {
                throw new UserDoesNotExist(username);
            }

            user = (User) var1.next();
        } while (!Objects.equals(username, user.getUsername()));
        return user;
    }*/
}
