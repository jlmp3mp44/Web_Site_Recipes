package com.kpi.recipes.api.controller.recipe;
import com.kpi.recipes.api.exception.RecipeAlreadyExistException;
import com.kpi.recipes.api.model.RecipeBody;
import com.kpi.recipes.model.Category;
import com.kpi.recipes.model.Recipe;
import com.kpi.recipes.model.User;
import com.kpi.recipes.service.CategoryService;
import com.kpi.recipes.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private RecipeService recipeService;
    private CategoryService categoryService;
    @GetMapping
    public List<Recipe> getRecipes(@AuthenticationPrincipal User user){
        return recipeService.getRecipes(user);
    }


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @PostMapping("/add")
    public ResponseEntity addRecipe(@AuthenticationPrincipal User user, @Valid @RequestBody RecipeBody recipeBody) {
        if (user == null) {
            System.out.println("wmwdmwkedmkewmdemdkm");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        try {
            recipeService.addRecipes(user, recipeBody);
            return ResponseEntity.ok().build();
        } catch (RecipeAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
