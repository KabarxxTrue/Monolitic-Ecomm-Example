package com.kabarxx.store_example.exceptions.product;

public class ProductWasNotDeletedException extends RuntimeException {
    public ProductWasNotDeletedException(String message) {
        super(message);
    }
}
