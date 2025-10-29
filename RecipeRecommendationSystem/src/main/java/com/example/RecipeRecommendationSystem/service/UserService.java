package com.example.RecipeRecommendationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RecipeRecommendationSystem.entity.User;
import com.example.RecipeRecommendationSystem.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public User updateUser(Long id, User user) {
		return userRepository.findById(id)
				.map(existing -> {
					existing.setUsername(user.getUsername());
					existing.setEmail(user.getEmail());
					// Update password only if provided (caller should provide hashed password)
					if (user.getPassword() != null) {
						existing.setPassword(user.getPassword());
					}
					existing.setDietaryPreferences(user.getDietaryPreferences());
					// createdAt should not be overwritten on update
					existing.setBirthDate(user.getBirthDate());
					return userRepository.save(existing);
				})
				.orElseGet(() -> {
					user.setUserId(id);
					return userRepository.save(user);
				});
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
