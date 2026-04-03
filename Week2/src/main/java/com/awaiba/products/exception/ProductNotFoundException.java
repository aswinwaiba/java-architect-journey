package com.awaiba.products.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productName) {
        super("Product with name " + productName + " not found");
    }
}
