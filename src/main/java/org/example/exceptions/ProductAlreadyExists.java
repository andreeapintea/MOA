package org.example.exceptions;

public class ProductAlreadyExists extends Exception {
    private String productName;

    public ProductAlreadyExists(String productName) {
        super(String.format("An identical product called '%s' already exists!", productName));
        this.productName = productName;
    }

    public String getProductName() {
        return this.productName;
    }
}
