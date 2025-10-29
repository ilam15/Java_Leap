package com.example.RecipeRecommendationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RecipeRecommendationSystem.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
} 

