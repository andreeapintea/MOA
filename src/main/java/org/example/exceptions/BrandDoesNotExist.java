package org.example.exceptions;

public class BrandDoesNotExist extends Exception {
    private String name;

    public BrandDoesNotExist(String name) {
        super(String.format("An brand with the username '%s' does not exist", name));
        this.name = name;
    }

    public String getUsername() {
        return this.name;
    }
}