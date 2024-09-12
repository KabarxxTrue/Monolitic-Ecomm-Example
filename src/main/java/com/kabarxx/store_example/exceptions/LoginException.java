package com.kabarxx.store_example.exceptions;

public class LoginException extends RuntimeException {
    public LoginException() {
        super("Login failed");
    }
}
