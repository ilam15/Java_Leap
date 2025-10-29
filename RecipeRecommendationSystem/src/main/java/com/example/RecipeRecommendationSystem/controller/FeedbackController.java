package com.example.RecipeRecommendationSystem.controller;

import com.example.RecipeRecommendationSystem.entity.Feedback;
import com.example.RecipeRecommendationSystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping(path = "/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Feedback>> getFeedbacksForRecipeApi(@PathVariable Long recipeId) {
        List<Feedback> list = feedbackService.getFeedbacksForRecipe(recipeId);
        return ResponseEntity.ok(list);
    }

    @PostMapping(path = "/{recipeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Feedback> addFeedbackForRecipeApi(@PathVariable Long recipeId,
                                                            @RequestParam Long userId,
                                                            @RequestBody Feedback feedback) {
        Feedback created = feedbackService.addFeedback(recipeId, userId, feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}


