package com.example.RecipeRecommendationSystem.controller.Router;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import com.example.RecipeRecommendationSystem.entity.Feedback;
import com.example.RecipeRecommendationSystem.entity.Recipe;
import com.example.RecipeRecommendationSystem.entity.User;
import com.example.RecipeRecommendationSystem.service.FeedbackService;
import com.example.RecipeRecommendationSystem.service.FavoriteService;
import com.example.RecipeRecommendationSystem.service.RecipeService;
import com.example.RecipeRecommendationSystem.service.UserService;

@Controller
public class PageController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String showHomePage(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Recipe> recipes;

        if (search != null && !search.isEmpty()) {
            recipes = recipeService.searchRecipes(search);
        } else {
            recipes = recipeService.getAllRecipes();
        }
        recipes = recipes == null ? Collections.emptyList() : recipes.stream().filter(Objects::nonNull).toList();
        model.addAttribute("recipes", recipes);
        model.addAttribute("search", search);
        return "Pages/Home";
    }

    @GetMapping("/")
    public String rootRedirect(HttpSession session) {
        Object uid = session.getAttribute("userId");
        if (uid != null) {
            return "redirect:/home";
        }
        return "redirect:/auth";
    }

    @GetMapping("/auth")
    public String showAuthPage(HttpSession session) {
        Object uid = session.getAttribute("userId");
        if (uid != null) {
            return "redirect:/home";
        }
        return "Pages/Auth";
    }

    @GetMapping("/recipes")
    public String showRecipePage(@RequestParam(value = "id", required = false) Long id, Model model, HttpSession session) {
        if (id != null) {
            Optional<Recipe> opt = recipeService.getRecipeById(id);
            if (opt.isPresent()) {
                Recipe recipe = opt.get();
                model.addAttribute("recipe", recipe);

                // Fetch feedbacks
                List<Feedback> feedbacks;
                try {
                    feedbacks = feedbackService.getFeedbacksForRecipe(id);
                } catch (Exception e) {
                    feedbacks = Collections.emptyList();
                }
                model.addAttribute("feedbacks", feedbacks);
                Long currentUserId = null;
                Object uid = session.getAttribute("userId");
                if (uid instanceof Long) {
                    currentUserId = (Long) uid;
                } else if (uid instanceof Integer) {
                    currentUserId = ((Integer) uid).longValue();
                }

                User currentUser = null;
                if (currentUserId != null) {
                    currentUser = userService.getUserById(currentUserId).orElse(null);
                }

                boolean isFavorited = false;
                if (currentUser != null) {
                    isFavorited = favoriteService.isRecipeFavoritedByUser(currentUser.getUserId(), recipe.getRecipeId());
                }
                model.addAttribute("isFavorited", isFavorited);

                // Ownership flag
                boolean isOwner = false;
                if (currentUser != null && recipe.getUser() != null) {
                    Object ownerObj = recipe.getUser();
                    if (ownerObj instanceof User) {
                        User owner = (User) ownerObj;
                        isOwner = owner.getUserId() != null && owner.getUserId().equals(currentUser.getUserId());
                    }
                }
                model.addAttribute("isOwner", isOwner);

                return "Pages/Recipe";
            }
            return "redirect:/home";
        }
        return "redirect:/home";
    }

    @GetMapping("/recipe/add")
    public String showAddRecipePage(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "Pages/AddRecipe";
    }

     @GetMapping("/favorites")
    public String showFavoritePage(Model model) {
        List<Recipe> favoriteRecipes = recipeService.getFavoriteRecipesForCurrentUser();
        model.addAttribute("favoriteRecipes", favoriteRecipes);
        return "Pages/Favorite";
    }
     @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session so server-side userId is removed and user is logged out
        try {
            session.invalidate();
        } catch (Exception ignored) {}
        return "redirect:/auth";
    }
}
