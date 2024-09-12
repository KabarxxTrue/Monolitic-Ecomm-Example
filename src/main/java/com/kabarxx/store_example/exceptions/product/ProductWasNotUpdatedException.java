package com.kabarxx.store_example.exceptions.product;

public class ProductWasNotUpdatedException extends RuntimeException {
    public ProductWasNotUpdatedException(String message) {
        super(message);
    }
}
