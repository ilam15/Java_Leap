package com.example.RecipeRecommendationSystem.controller;

import com.example.RecipeRecommendationSystem.entity.Recommendation;
import com.example.RecipeRecommendationSystem.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping
    public List<Recommendation> getAllRecommendations(@RequestParam(required = false) Long userId) {
        if (userId != null) {
            return recommendationService.getRecommendationsForUser(userId);
        }
        return recommendationService.getAllRecommendations();
    }

    @GetMapping("/{id}")
    public Recommendation getRecommendationById(@PathVariable Long id) {
        return recommendationService.getRecommendationById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recommendation not found with ID: " + id));
    }

    @PostMapping
    public Recommendation createRecommendation(@RequestBody Recommendation recommendation) {
        return recommendationService.createRecommendation(recommendation);
    }

    @PutMapping("/{id}")
    public Recommendation updateRecommendation(@PathVariable Long id, @RequestBody Recommendation recommendation) {
        return recommendationService.updateRecommendation(id, recommendation);
    }

    @DeleteMapping("/{id}")
    public String deleteRecommendation(@PathVariable Long id) {
        recommendationService.deleteRecommendation(id);
        return "Recommendation deleted successfully";
    }
}

