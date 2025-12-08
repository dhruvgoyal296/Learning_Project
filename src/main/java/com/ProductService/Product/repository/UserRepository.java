package com.ProductService.Product.repository;

import com.ProductService.Product.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional <User> findByUsername(String username);
    Optional <User> findByEmail(String email);
}
