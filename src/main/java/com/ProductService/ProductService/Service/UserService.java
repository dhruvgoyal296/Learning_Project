package com.ProductService.ProductService.Service;

import com.ProductService.ProductService.Entity.SignUpRequest;
import com.ProductService.ProductService.Entity.User;
import com.ProductService.ProductService.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    public User registerUser (SignUpRequest request) {
        logger.debug("User registration attempt for username: {}", request.getUsername());
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            logger.warn("Username already exists: {}", request.getUsername());
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            logger.warn("Email already exists: {}", request.getEmail());
            throw new RuntimeException("Email already exists");
        }
        User newUser = new User();
        String passwordEncoder1 = passwordEncoder.encode(request.getPassword());
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder1);
        newUser.setRole("USER");
        logger.debug("User registered successfully with username: {}", request.getUsername());
        return userRepository.save(newUser);
    }
}
