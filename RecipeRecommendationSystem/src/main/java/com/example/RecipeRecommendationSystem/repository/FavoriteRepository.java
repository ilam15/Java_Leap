package com.example.RecipeRecommendationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RecipeRecommendationSystem.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
} 

