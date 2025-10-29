package com.example.RecipeRecommendationSystem.service;

import com.example.RecipeRecommendationSystem.entity.Feedback;
import com.example.RecipeRecommendationSystem.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.RecipeRecommendationSystem.entity.User;
import com.example.RecipeRecommendationSystem.entity.Recipe;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public void createFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public Optional<Feedback> getFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }

    public void updateFeedback(Long feedbackId, Feedback updatedFeedback) {
        Feedback existing = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found with ID: " + feedbackId));

        existing.setRating(updatedFeedback.getRating());
        existing.setComment(updatedFeedback.getComment());
        existing.setRecipe(updatedFeedback.getRecipe());
        existing.setUser(updatedFeedback.getUser());

        feedbackRepository.save(existing);
    }

    public void deleteFeedback(Long feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    /**
     * Create feedback for a recipe by a user.
     */
    @Transactional
    public Feedback addFeedback(Long recipeId, Long userId, Feedback feedback) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Recipe recipe = recipeService.getRecipeById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + recipeId));

        feedback.setUser(user);
        feedback.setRecipe(recipe);
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbacksForRecipe(Long recipeId) {
        return feedbackRepository.findByRecipeRecipeId(recipeId);
    }
}

