package com.example.RecipeRecommendationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import com.example.RecipeRecommendationSystem.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	List<Favorite> findByUserUserId(Long userId);

	Optional<Favorite> findByUserUserIdAndRecipeRecipeId(Long userId, Long recipeId);

	void deleteByUserUserIdAndRecipeRecipeId(Long userId, Long recipeId);

    boolean existsByUser_UserIdAndRecipe_RecipeId(Long userId, Long recipeId);
}

