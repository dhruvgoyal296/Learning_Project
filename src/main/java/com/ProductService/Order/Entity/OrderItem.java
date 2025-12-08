package com.ProductService.Order.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "order_items")
@Data
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
