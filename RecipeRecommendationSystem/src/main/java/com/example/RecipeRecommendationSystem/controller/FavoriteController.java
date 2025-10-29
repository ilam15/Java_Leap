package com.example.RecipeRecommendationSystem.controller;

import com.example.RecipeRecommendationSystem.entity.Favorite;
import com.example.RecipeRecommendationSystem.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public String getAllFavorites(Model model) {
        List<Favorite> favorites = favoriteService.getAllFavorites();
        model.addAttribute("favorites", favorites);
        return "favorites/list";
    }

    @GetMapping("/new")
    public String createFavoriteForm(Model model) {
        model.addAttribute("favorite", new Favorite());
        return "favorites/form";
    }

    @PostMapping
    public String createFavorite(@ModelAttribute Favorite favorite) {
        favoriteService.createFavorite(favorite);
        return "redirect:/favorites";
    }

    @GetMapping("/edit/{favoriteId}")
    public String editFavoriteForm(@PathVariable Long favoriteId, Model model) {
        model.addAttribute("favorite", favoriteService.getFavoriteById(favoriteId).orElseThrow());
        return "favorites/form";
    }

    @PostMapping("/update/{favoriteId}")
    public String updateFavorite(@PathVariable Long favoriteId, @ModelAttribute Favorite favorite) {
        favoriteService.updateFavorite(favoriteId, favorite);
        return "redirect:/favorites";
    }

    @GetMapping("/delete/{favoriteId}")
    public String deleteFavorite(@PathVariable Long favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
        return "redirect:/favorites";
    }

}

