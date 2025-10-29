package com.example.RecipeRecommendationSystem.service;

import com.example.RecipeRecommendationSystem.entity.Feedback;
import com.example.RecipeRecommendationSystem.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

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
}

