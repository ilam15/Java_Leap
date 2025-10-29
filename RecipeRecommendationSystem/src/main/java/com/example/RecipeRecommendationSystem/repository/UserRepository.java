package com.example.RecipeRecommendationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RecipeRecommendationSystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
} 