package org.example.exceptions;

public class ProductNotInStock extends Exception {

    public ProductNotInStock() {
        super(String.format("The product is out of stock!"));
    }

}
