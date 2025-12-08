package com.ProductService.Order.Service;

import com.ProductService.Order.DTO.OrderProductRequest;
import com.ProductService.Order.DTO.OrderRequest;
import com.ProductService.Order.DTO.OrderResponse;
import com.ProductService.Order.Entity.OrderItem;
import com.ProductService.Order.Entity.OrderStatus;
import com.ProductService.Order.repository.OrderRepository;
import com.ProductService.Order.Entity.Order;
import com.ProductService.Product.Entity.Product;
import com.ProductService.Product.Service.ProductService;
import com.ProductService.Product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public OrderResponse createOrder (OrderRequest request, Long userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderStatus(OrderStatus.CREATED);

        Double totalAmount = 0.0;
        List<OrderItem> items = new ArrayList<>();
        for (OrderProductRequest p : request.getProducts()) {
            Product product = productService.getProductById(p.getProductId()).getBody();
            if (product.getQuantity() < p.getQuantity()) {
                throw new RuntimeException("Not enough stock for product : " + product.getName());
            }
            Double price = product.getPrice();
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(price);
            orderItem.setQuantity(p.getQuantity());
            orderItem.setProductId(p.getProductId());
            orderItem.setProductName(product.getName());
            orderItem.setOrder(order);
            items.add(orderItem);

            product.setQuantity(product.getQuantity() - p.getQuantity());
            productService.addProduct(product);

            totalAmount += (price * p.getQuantity());
        }
        order.setItems(items);
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        return new OrderResponse(order.getId(), order.getTotalAmount(), order.getOrderStatus());
    }
}
