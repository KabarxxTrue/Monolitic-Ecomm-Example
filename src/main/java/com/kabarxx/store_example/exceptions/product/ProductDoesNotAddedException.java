package com.kabarxx.store_example.exceptions.product;

public class ProductDoesNotAddedException extends RuntimeException {
    public ProductDoesNotAddedException(String message) {
        super(message);
    }
}
