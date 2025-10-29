package com.example.RecipeRecommendationSystem.controller;

import com.example.RecipeRecommendationSystem.dto.LoginRequest;
import com.example.RecipeRecommendationSystem.entity.User;
import com.example.RecipeRecommendationSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User created = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}
