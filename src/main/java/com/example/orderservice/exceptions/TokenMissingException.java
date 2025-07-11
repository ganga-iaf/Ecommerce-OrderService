package com.example.orderservice.exceptions;

public class TokenMissingException extends Exception {
    public TokenMissingException(String message) {
        super(message);
    }
}
