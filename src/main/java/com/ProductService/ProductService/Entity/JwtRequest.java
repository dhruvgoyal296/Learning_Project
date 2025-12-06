package com.ProductService.ProductService.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}

