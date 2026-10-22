package com.example.products.exception;

public class NoStockException extends RuntimeException {
    public NoStockException(String message) {
        super(message);
    }
}