package com.example.RecipeRecommendationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RecipeRecommendationSystem.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
} 

