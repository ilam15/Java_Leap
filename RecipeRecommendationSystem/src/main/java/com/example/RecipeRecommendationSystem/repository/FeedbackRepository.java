package com.example.RecipeRecommendationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.example.RecipeRecommendationSystem.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	List<Feedback> findByRecipeRecipeId(Long recipeId);
}

