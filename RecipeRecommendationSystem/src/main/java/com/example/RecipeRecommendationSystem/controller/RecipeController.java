package com.example.RecipeRecommendationSystem.controller;

import com.example.RecipeRecommendationSystem.entity.Recipe;
import com.example.RecipeRecommendationSystem.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe created = recipeService.createRecipe(recipe);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getRecipeId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> list = recipeService.getAllRecipes();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable("id") Long id) {
        Optional<Recipe> opt = recipeService.getRecipeById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable("id") Long id, @RequestBody Recipe recipe) {
        try {
            Recipe updated = recipeService.updateRecipe(id, recipe);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("id") Long id) {
        if (!recipeService.getRecipeById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
        }
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    public String addRecipe(@ModelAttribute Recipe recipe) {
    recipeService.createRecipe(recipe);
    return "redirect:/home";
    }
}
