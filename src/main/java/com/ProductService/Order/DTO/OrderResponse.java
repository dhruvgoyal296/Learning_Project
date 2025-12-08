package com.ProductService.Order.DTO;

import com.ProductService.Order.Entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Double payment;
    private OrderStatus status;
}
