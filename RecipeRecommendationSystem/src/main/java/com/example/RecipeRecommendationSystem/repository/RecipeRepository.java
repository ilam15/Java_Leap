package com.example.RecipeRecommendationSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RecipeRecommendationSystem.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByTitleContainingIgnoreCase(String keyword);
} 

