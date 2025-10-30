package com.example.RecipeRecommendationSystem.service;

import com.example.RecipeRecommendationSystem.entity.Recipe;
import com.example.RecipeRecommendationSystem.entity.Recommendation;
import com.example.RecipeRecommendationSystem.entity.User;
import com.example.RecipeRecommendationSystem.repository.RecipeRepository;
import com.example.RecipeRecommendationSystem.repository.RecommendationRepository;
import com.example.RecipeRecommendationSystem.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }

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

    private double calculateSimilarity(Set<String> userIngredients, Set<String> recipeIngredients) {
        Set<String> intersection = new HashSet<>(userIngredients);
        intersection.retainAll(recipeIngredients);

        Set<String> union = new HashSet<>(userIngredients);
        union.addAll(recipeIngredients);

        return union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();
    }

    public List<Recipe> recommendRecipes(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return Collections.emptyList();

        User user = userOpt.get();
        String prefs = user.getDietaryPreferences();
        if (prefs == null || prefs.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> userIngredients = Arrays.stream(prefs.toLowerCase().split(","))
                .map(String::trim)
                .collect(Collectors.toSet());

        List<Recipe> allRecipes = recipeRepository.findAll();

        List<Map.Entry<Recipe, Double>> scored = allRecipes.stream()
                .map(recipe -> {
                    Set<String> recipeIngredients = Arrays.stream(recipe.getIngredients().toLowerCase().split(","))
                            .map(String::trim)
                            .collect(Collectors.toSet());
                    double score = calculateSimilarity(userIngredients, recipeIngredients);
                    return Map.entry(recipe, score);
                })
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) 
                .toList();

        return scored.stream()
                .filter(e -> e.getValue() > 0)
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }
}
