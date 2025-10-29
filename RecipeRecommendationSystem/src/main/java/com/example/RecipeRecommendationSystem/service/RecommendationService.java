package com.example.RecipeRecommendationSystem.service;

import com.example.RecipeRecommendationSystem.entity.Recommendation;
import com.example.RecipeRecommendationSystem.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }

    /**
     * Get personalized recommendations for a specific user, ordered by match score desc.
     */
    public List<Recommendation> getRecommendationsForUser(Long userId) {
        return recommendationRepository.findByUserUserIdOrderByMatchScoreDesc(userId);
    }

    public Recommendation createRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public Optional<Recommendation> getRecommendationById(Long recommendationId) {
        return recommendationRepository.findById(recommendationId);
    }

    public Recommendation updateRecommendation(Long recommendationId, Recommendation updated) {
        Recommendation existing = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new IllegalArgumentException("Recommendation not found with ID: " + recommendationId));

        existing.setUser(updated.getUser());
        existing.setRecipe(updated.getRecipe());
        existing.setMatchScore(updated.getMatchScore());

        return recommendationRepository.save(existing);
    }

    public void deleteRecommendation(Long recommendationId) {
        if (!recommendationRepository.existsById(recommendationId)) {
            throw new IllegalArgumentException("Recommendation not found with ID: " + recommendationId);
        }
        recommendationRepository.deleteById(recommendationId);
    }
}

