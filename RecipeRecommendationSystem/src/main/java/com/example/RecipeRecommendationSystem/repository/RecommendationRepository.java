package com.example.RecipeRecommendationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.example.RecipeRecommendationSystem.entity.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
	List<Recommendation> findByUserUserIdOrderByMatchScoreDesc(Long userId);
}