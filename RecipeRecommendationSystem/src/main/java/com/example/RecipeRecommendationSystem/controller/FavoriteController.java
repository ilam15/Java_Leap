package com.example.RecipeRecommendationSystem.controller;

import com.example.RecipeRecommendationSystem.entity.Favorite;
import com.example.RecipeRecommendationSystem.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public String getAllFavorites(Model model) {
        List<Favorite> favorites = favoriteService.getAllFavorites();
        model.addAttribute("favorites", favorites);
        return "favorites/list";
    }

    // --- REST API: List favorites (JSON) ---
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Favorite>> getAllFavoritesApi(@RequestParam(required = false) Long userId) {
        if (userId != null) {
            return ResponseEntity.ok(favoriteService.getFavoritesForUser(userId));
        }
        return ResponseEntity.ok(favoriteService.getAllFavorites());
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

    // --- REST API: Add favorite by recipeId (requires userId query param) ---
    @PostMapping(value = "/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Favorite> addFavoriteApi(@PathVariable Long recipeId, @RequestParam Long userId) {
        Favorite fav = favoriteService.addFavorite(userId, recipeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(fav);
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

    // --- REST API: Remove favorite for a given recipeId and userId ---
    @DeleteMapping(value = "/{recipeId}")
    @ResponseBody
    public ResponseEntity<?> removeFavoriteApi(@PathVariable Long recipeId, @RequestParam Long userId) {
        favoriteService.removeFavorite(userId, recipeId);
        return ResponseEntity.ok("Favorite removed successfully");
    }

    // --- Form endpoint used by recipe page to add a favorite for current session user ---
    @PostMapping("/add/{recipeId}")
    public String addFavoriteForm(@PathVariable Long recipeId, HttpSession session) {
        Object uid = session.getAttribute("userId");
        Long userId = null;
        if (uid instanceof Long) userId = (Long) uid;
        else if (uid instanceof Integer) userId = ((Integer) uid).longValue();
        if (userId == null) {
            // no user in session -> redirect to auth
            return "redirect:/auth";
        }
        favoriteService.addFavorite(userId, recipeId);
        return "redirect:/recipes?id=" + recipeId;
    }

}

