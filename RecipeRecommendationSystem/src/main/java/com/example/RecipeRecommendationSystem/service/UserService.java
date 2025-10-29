package com.example.RecipeRecommendationSystem.service;

import com.example.RecipeRecommendationSystem.entity.User;
import com.example.RecipeRecommendationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setDietaryPreferences(updatedUser.getDietaryPreferences());
        existingUser.setBirthDate(updatedUser.getBirthDate());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public User registerUser(User user) {
        return createUser(user);
    }

    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword() != null && user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> getProfile(Long userId) {
        return getUserById(userId);
    }
}
