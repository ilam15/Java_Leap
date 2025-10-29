package com.example.RecipeRecommendationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RecipeRecommendationSystem.entity.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
} 