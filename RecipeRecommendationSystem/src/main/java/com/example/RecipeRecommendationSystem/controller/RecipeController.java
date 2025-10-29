package com.example.RecipeRecommendationSystem.controller;

import com.example.RecipeRecommendationSystem.entity.Recipe;
import com.example.RecipeRecommendationSystem.service.RecipeService;
import com.example.RecipeRecommendationSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllRecipes(Model model) {
        List<Recipe> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        return "recipes/list";  // Thymeleaf template
    }

    @GetMapping("/new")
    public String createRecipeForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("users", userService.getAllUsers()); // for assigning creator
        return "recipes/form";
    }

    @PostMapping
    public String createRecipe(@ModelAttribute Recipe recipe) {
        recipeService.createRecipe(recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/edit/{recipeId}")
    public String editRecipeForm(@PathVariable Long recipeId, Model model) {
        Recipe recipe = recipeService.getRecipeById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid recipe ID: " + recipeId));
        model.addAttribute("recipe", recipe);
        model.addAttribute("users", userService.getAllUsers());
        return "recipes/form";
    }

    // ✅ Update an existing recipe
    @PostMapping("/update/{recipeId}")
    public String updateRecipe(@PathVariable Long recipeId, @ModelAttribute Recipe recipe) {
        recipeService.updateRecipe(recipeId, recipe);
        return "redirect:/recipes";
    }

    // ✅ Delete a recipe
    @GetMapping("/delete/{recipeId}")
    public String deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return "redirect:/recipes";
    }

    @GetMapping("/{recipeId}")
    public String viewRecipe(@PathVariable Long recipeId, Model model) {
        Recipe recipe = recipeService.getRecipeById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid recipe ID: " + recipeId));
        model.addAttribute("recipe", recipe);
        return "recipes/view";  // optional view page
    }
}
