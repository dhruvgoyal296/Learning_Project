package com.ProductService.Product.exceptions;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException (String message) {
        super(message);
    }
}
