package com.ProductService.Product.Controller;

import com.ProductService.Product.Entity.Product;
import com.ProductService.Product.Service.ProductService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct (@RequestBody Product p) {
        logger.info("Adding new product: {}", p.getName());
        ResponseEntity<Product> newProduct = productService.addProduct(p);
        logger.info("Product added with ID: {}", newProduct.getBody().getId());
        return newProduct;
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

    @GetMapping("/all" )
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }
}
