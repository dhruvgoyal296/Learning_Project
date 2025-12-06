package com.ProductService.ProductService.Controller;

import com.ProductService.ProductService.Entity.Product;
import com.ProductService.ProductService.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct (@RequestBody Product p) {
        return productService.addProduct(p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/{id}/reduce")
    public ResponseEntity<String> reduceQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        return productService.reduceQuantity(id, quantity);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
