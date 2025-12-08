package com.ProductService.Product.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException (String message) {
        super(message);
    }
}
