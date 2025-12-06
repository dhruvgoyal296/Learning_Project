package com.ProductService.ProductService.exceptions;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException (String message) {
        super(message);
    }
}
