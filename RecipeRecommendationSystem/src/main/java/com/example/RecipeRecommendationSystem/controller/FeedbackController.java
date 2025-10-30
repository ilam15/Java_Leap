package com.example.RecipeRecommendationSystem.controller;

import com.example.RecipeRecommendationSystem.entity.Feedback;
import com.example.RecipeRecommendationSystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpSession;

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

    @PostMapping("/add")
    public String addFeedbackForm(@RequestParam Long recipeId, @RequestParam(name = "comment") String comment, HttpSession session) {
        Object uid = session.getAttribute("userId");
        Long userId = null;
        try {
            if (uid instanceof Long) userId = (Long) uid;
            else if (uid instanceof Integer) userId = ((Integer) uid).longValue();
            else if (uid instanceof String) userId = Long.parseLong((String) uid);
        } catch (Exception e) {
            // fall through to check null below
        }

        if (userId == null) {
            // not logged in
            return "redirect:/auth";
        }

        try {
            Feedback feedback = new Feedback();
            feedback.setComment(comment == null ? "" : comment.trim());
            feedbackService.addFeedback(recipeId, userId, feedback);
            return "redirect:/recipes?id=" + recipeId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/recipes?id=" + recipeId + "&feedbackError=" + java.net.URLEncoder.encode(ex.getMessage() == null ? "error" : ex.getMessage(), java.nio.charset.StandardCharsets.UTF_8);
        }
    }
}


