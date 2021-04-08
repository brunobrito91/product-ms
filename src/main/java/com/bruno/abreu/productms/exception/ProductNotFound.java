package com.bruno.abreu.productms.exception;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound() {
        super("Product not found!");
    }
}
