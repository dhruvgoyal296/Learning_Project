package com.ProductService.ProductService.Controller;

import com.ProductService.ProductService.Entity.JwtRequest;
import com.ProductService.ProductService.Entity.JwtResponse;
import com.ProductService.ProductService.Entity.SignUpRequest;
import com.ProductService.ProductService.Entity.User;
import com.ProductService.ProductService.Service.UserService;
import com.ProductService.ProductService.Utility.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.net.Authenticator;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login (@RequestBody JwtRequest request) {
        logger.info("Login attempt for user: {}", request.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            logger.info("Authentication successful for user: {}", request.getUsername());
        }
        catch (BadCredentialsException e) {
            logger.warn("Invalid credentials for user: {}", request.getUsername());
            throw new RuntimeException("Invalid Credentials");
        }
        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        logger.info("JWT token generated for user: {}", request.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup (@RequestBody SignUpRequest request) {
        logger.info("Signup attempt for user: {}", request.getUsername());
        try {
            User user = userService.registerUser(request);
        }
        catch (Exception e) {
            logger.warn("Signup failed for user: {}: {}", request.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        logger.info("User registered successfully: {}", request.getUsername());
        return ResponseEntity.ok("User Registered Successfully");
    }
}
