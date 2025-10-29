package com.example.RecipeRecommendationSystem.controller;

import com.example.RecipeRecommendationSystem.entity.Feedback;
import com.example.RecipeRecommendationSystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public String getAllFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "feedbacks/list";
    }

    @GetMapping("/new")
    public String createFeedbackForm(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "feedbacks/form";
    }

    @PostMapping
    public String createFeedback(@ModelAttribute Feedback feedback) {
        feedbackService.createFeedback(feedback);
        return "redirect:/feedbacks";
    }

    @GetMapping("/edit/{feedbackId}")
    public String editFeedbackForm(@PathVariable Long feedbackId, Model model) {
        model.addAttribute("feedback", feedbackService.getFeedbackById(feedbackId).orElseThrow());
        return "feedbacks/form";
    }

    @PostMapping("/update/{feedbackId}")
    public String updateFeedback(@PathVariable Long feedbackId, @ModelAttribute Feedback feedback) {
        feedbackService.updateFeedback(feedbackId, feedback);
        return "redirect:/feedbacks";
    }

    @GetMapping("/delete/{feedbackId}")
    public String deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return "redirect:/feedbacks";
    }

}

