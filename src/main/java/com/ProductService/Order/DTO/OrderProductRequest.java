package com.ProductService.Order.DTO;

import lombok.Data;

@Data
public class OrderProductRequest {
    private Long productId;
    private Integer quantity;
}
