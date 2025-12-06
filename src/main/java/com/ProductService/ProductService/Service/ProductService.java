package com.ProductService.ProductService.Service;

import com.ProductService.ProductService.Entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ResponseEntity<Product> addProduct(Product product);
    ResponseEntity<Product> getProductById(Long productId);
    ResponseEntity<String> reduceQuantity(Long productId, Integer quantity);
    ResponseEntity<List <Product>> getAllProducts();
    ResponseEntity<Product> updateProduct(Long productId, Product product);
    ResponseEntity<Product> deleteProduct(Long id);
}
