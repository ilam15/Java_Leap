package com.example.RecipeRecommendationSystem.controller.Router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/auth")
    public String showAuthPage() {
        return "Pages/Auth"; 
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "Pages/Home"; // refers to home.html
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