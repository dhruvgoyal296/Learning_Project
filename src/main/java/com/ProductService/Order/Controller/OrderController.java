package com.ProductService.Order.Controller;

import com.ProductService.Order.DTO.OrderRequest;
import com.ProductService.Order.DTO.OrderResponse;
import com.ProductService.Order.Service.OrderService;
import com.ProductService.Order.repository.OrderRepository;
import com.ProductService.Product.Entity.User;
import com.ProductService.Product.repository.UserRepository;
import com.ProductService.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder (@RequestBody OrderRequest request, @RequestHeader String token) {

        String userName = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findByUsername(userName);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Long userId = user.get().getId();

        OrderResponse order = orderService.createOrder(request, userId);
        return ResponseEntity.ok(order);
    }
}
