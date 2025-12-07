package com.ProductService.ProductService.Service;

import com.ProductService.ProductService.Entity.Product;
import com.ProductService.ProductService.exceptions.InsufficientStockException;
import com.ProductService.ProductService.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Check for updateProduct whose product id does not exist.

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    @Transactional
    public ResponseEntity<Product> addProduct(Product product) {
        logger.debug("Entering addProduct method with product: {}", product.getName());
        Product p = productRepository.save(product);
        System.out.println("Before the flust method : " + p.getId());
        productRepository.flush();
        System.out.println("After the flush method : " + p.getId());
        logger.debug("Product added with ID: {}", p.getId());
        return ResponseEntity.ok(p);
    }

    @Override
    public ResponseEntity<Product> getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(null);
        }
    }

    @Override
    public ResponseEntity<String> reduceQuantity(Long productId, Integer quantity) {
        Product product = getProductById(productId).getBody();
        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient quantity for " + product.getName());
        }
        else {
            product.setQuantity(product.getQuantity() - quantity);
            Product product1 = productRepository.save(product);
            return ResponseEntity.ok("Quantity reduced successfully");
        }
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        List <Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<Product> updateProduct(Long productId, Product product) {
        Product existingProduct = getProductById(productId).getBody();
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        Product product1 = productRepository.save(existingProduct);
        return ResponseEntity.ok(product1);
    }

    @Override
    public ResponseEntity<Product> deleteProduct(Long id) {
        Product existingProduct = getProductById(id).getBody();
        if (existingProduct != null) {
            productRepository.deleteById(id);
            return ResponseEntity.ok(existingProduct);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(null);
        }
    }
}
