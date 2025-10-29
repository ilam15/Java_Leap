package com.example.RecipeRecommendationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.RecipeRecommendationSystem.entity.User;
import com.example.RecipeRecommendationSystem.service.UserService;
import java.util.List;


@RestController
@RequestMapping("/api/users") 
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

    // ✅ Delete a user
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "User deleted successfully";
    }
}
