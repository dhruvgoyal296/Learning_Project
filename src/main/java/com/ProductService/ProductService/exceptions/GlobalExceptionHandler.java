package com.ProductService.ProductService.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductNotFoundException.class)
    ResponseEntity<Map<String, Object>> handleProductNotFoundException (ProductNotFoundException e, HttpServletRequest request) {
        Map <String, Object> error = new HashMap<>();
        error.put("message", e.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("timestamp", LocalDateTime.now());
        error.put("path", request.getRequestURI());
        logger.error("ProductNotFoundException: {}", e.getMessage());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(InsufficientStockException.class)
    ResponseEntity<Map<String, Object>> handleInsufficientStockException (InsufficientStockException e, HttpServletRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", e.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("path", request.getRequestURI());
        error.put("timestamp", LocalDateTime.now());
        logger.error("handleInsufficientStockException: {}", e.getMessage());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<Map <String, Object>> handleUsernameNotFoundException (UsernameNotFoundException e, HttpServletRequest request) {
        Map <String, Object> error = new HashMap<>();
        error.put("message", e.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("path", request.getRequestURI());
        error.put("timestamp", LocalDateTime.now());
        logger.error("handleUsernameNotFoundException: {}", e.getMessage());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Map<String, Object>> handleGenericException (Exception e, HttpServletRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("message", e.getMessage());
        error.put("path", request.getRequestURI());
        error.put("timestamp", LocalDateTime.now());
        logger.error("handleGenericException: {}", e.getMessage());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

}
