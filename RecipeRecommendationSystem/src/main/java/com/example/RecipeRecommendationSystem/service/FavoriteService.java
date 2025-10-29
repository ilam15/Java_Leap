package com.example.RecipeRecommendationSystem.service;

import com.example.RecipeRecommendationSystem.entity.Favorite;
import com.example.RecipeRecommendationSystem.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

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
}

