package org.example.model;

import javafx.scene.image.Image;

import java.util.Objects;

public class Product {

    private String brandName;
    private String productName;
    private String brandUsername;
    private String imageUrl;
    private int quantity;
    private double price;
    private String measurement;
    private int noOfItems;

    public Product(){

    }
    public Product(String brandName, String productName, String brandUsername, String imageUrl, int quantity, double price, String measurement, int noOfItems) {
        this.brandName = brandName;
        this.productName = productName;
        this.brandUsername = brandUsername;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
        this.measurement=measurement;
        this.noOfItems=noOfItems;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public String getStringNoOfItems() {
        return Integer.toString(noOfItems);
    }

    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandUsername() {
        return brandUsername;
    }

    public void setBrandUsername(String brandUsername) {
        this.brandUsername = brandUsername;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getStringQuantity() {
        return Integer.toString(quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getPrice() {
        return price;
    }

    public String getStringPrice() {
        return Double.toString(price);
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity &&
                Double.compare(product.price, price) == 0 &&
                Objects.equals(brandName, product.brandName) &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(brandUsername, product.brandUsername) &&
                Objects.equals(imageUrl, product.imageUrl) &&
                Objects.equals(measurement, product.measurement);
    }

    public int hashCode() {
        return Objects.hash(brandName, productName, brandUsername, imageUrl, quantity, price, measurement);
    }

    @Override
    public String toString() {
        return productName + ", " + quantity + measurement + ", Bucati in stoc: " + noOfItems + ", "  + this.price + "lei";
    }
}
