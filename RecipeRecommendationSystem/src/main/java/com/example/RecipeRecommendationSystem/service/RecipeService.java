package com.example.RecipeRecommendationSystem.service;

import com.example.RecipeRecommendationSystem.entity.Recipe;
import com.example.RecipeRecommendationSystem.entity.User;
import com.example.RecipeRecommendationSystem.repository.RecipeRepository;
import com.example.RecipeRecommendationSystem.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> getRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId);
    }

    public Recipe updateRecipe(Long recipeId, Recipe updatedRecipe) {
        Recipe existing = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + recipeId));

        existing.setTitle(updatedRecipe.getTitle());
        existing.setDescription(updatedRecipe.getDescription());
        existing.setIngredients(updatedRecipe.getIngredients());
        existing.setInstructions(updatedRecipe.getInstructions());
        existing.setCalories(updatedRecipe.getCalories());
        existing.setCategory(updatedRecipe.getCategory());
        existing.setCreatedBy(updatedRecipe.getCreatedBy());

        return recipeRepository.save(existing);
    }

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    public List<Recipe> searchRecipes(String keyword) {
        return recipeRepository.findByTitleContainingIgnoreCase(keyword);
    }

   public List<Recipe> getFavoriteRecipesForCurrentUser() {
    // 🔹 Temporary user for demo purposes
    Long userId = 1L;

    // Validate and fetch user
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

    // Return user's favorite recipes
    return user.getFavoriteRecipes();
}
}
