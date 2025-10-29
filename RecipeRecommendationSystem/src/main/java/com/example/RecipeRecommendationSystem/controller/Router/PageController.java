package com.example.RecipeRecommendationSystem.controller.Router;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RecipeRecommendationSystem.entity.Recipe;
import com.example.RecipeRecommendationSystem.service.RecipeService;

import org.springframework.ui.Model;

@Controller
public class PageController {


     @Autowired
    private RecipeService recipeService;

    @GetMapping("/home")
    public String showHomePage(@RequestParam(value = "search", required = false) String search,Model model) {
        List<Recipe> recipes;

        if (search != null && !search.isEmpty()) {
            recipes = recipeService.searchRecipes(search); 
        } else {
            recipes = recipeService.getAllRecipes();
        }
        model.addAttribute("recipes", recipes);
        model.addAttribute("search", search);
        return "Pages/Home";
    }

    @GetMapping("/auth")
    public String showAuthPage() {
        return "Pages/Auth"; 
    }

    
    @GetMapping("/profile")
    public String showProfilePage() {
        return "Pages/Profile"; // refers to profile.html
    }

    @GetMapping("/recipes")
    public String showRecipePage() {
        return "Pages/recipe"; // refers to recipes.html
    }

    @GetMapping("/recipes/add")
    public String showAddRecipePage() {
        return "Pages/AddRecipe"; // refers to recipes.html
    }

    @GetMapping("/admin")
    public String showAdminPage() {
        return "Admin"; // refers to admin.html
    }
}