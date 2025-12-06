package com.ProductService.ProductService.repository;

import com.ProductService.ProductService.Entity.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDao, Long> {
    Optional <UserDao> findByUsername(String username);
}
