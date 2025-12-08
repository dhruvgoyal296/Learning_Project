package com.ProductService.Order.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String paymentOrderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List <OrderItem> items;
}
