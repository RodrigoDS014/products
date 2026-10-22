package com.example.products.exception;

public class StockException extends RuntimeException {
    
    public StockException(String message) {
        super(message);
    }
}