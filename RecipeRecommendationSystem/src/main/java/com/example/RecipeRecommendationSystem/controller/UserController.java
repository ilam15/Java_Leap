package com.example.RecipeRecommendationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.RecipeRecommendationSystem.entity.User;
import com.example.RecipeRecommendationSystem.service.UserService;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user") 
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ✅ Get user by ID
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
    }

    // ✅ Create a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // ✅ Update a user
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "User deleted successfully";
    }
     @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam(name = "id", required = false) Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required query parameter: id");
        }
        Optional<User> userOpt = userService.getProfile(userId);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
