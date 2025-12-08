package com.ProductService.Order.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    List<OrderProductRequest> products;
}
