package com.example.RecipeRecommendationSystem.service;

import com.example.RecipeRecommendationSystem.entity.Favorite;
import com.example.RecipeRecommendationSystem.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.RecipeRecommendationSystem.entity.User;
import com.example.RecipeRecommendationSystem.entity.Recipe;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    public void createFavorite(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    public Optional<Favorite> getFavoriteById(Long favoriteId) {
        return favoriteRepository.findById(favoriteId);
    }

    public void updateFavorite(Long favoriteId, Favorite updatedFavorite) {
        Favorite existing = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new IllegalArgumentException("Favorite not found with ID: " + favoriteId));

        existing.setUser(updatedFavorite.getUser());
        existing.setRecipe(updatedFavorite.getRecipe());

        favoriteRepository.save(existing);
    }

    public void deleteFavorite(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

    /**
     * Add a favorite for a user and recipe.
     */
    @Transactional
    public Favorite addFavorite(Long userId, Long recipeId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Recipe recipe = recipeService.getRecipeById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + recipeId));

        // avoid duplicates: if exists, return existing
        Optional<Favorite> existing = favoriteRepository.findByUserUserIdAndRecipeRecipeId(userId, recipeId);
        if (existing.isPresent()) return existing.get();

        Favorite fav = new Favorite();
        fav.setUser(user);
        fav.setRecipe(recipe);
        return favoriteRepository.save(fav);
    }

    /**
     * Remove a favorite by userId and recipeId.
     */
    @Transactional
    public void removeFavorite(Long userId, Long recipeId) {
        Optional<Favorite> existing = favoriteRepository.findByUserUserIdAndRecipeRecipeId(userId, recipeId);
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            return;
        }
        throw new IllegalArgumentException("Favorite not found for userId=" + userId + " and recipeId=" + recipeId);
    }

    public List<Favorite> getFavoritesForUser(Long userId) {
        return favoriteRepository.findByUserUserId(userId);
    }
}

