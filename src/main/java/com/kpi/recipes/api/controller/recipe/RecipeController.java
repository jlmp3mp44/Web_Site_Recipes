package com.kpi.recipes.api.controller.recipe;
import com.kpi.recipes.api.exception.RecipeAlreadyExistException;
import com.kpi.recipes.api.exception.RecipeNotFoundException;
import com.kpi.recipes.api.model.RecipeBody;
import com.kpi.recipes.model.Recipe;
import com.kpi.recipes.model.User;
import com.kpi.recipes.service.AuthorizationService;
import com.kpi.recipes.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private RecipeService recipeService;
    private AuthorizationService authorizationService;
    @GetMapping
    public List<Recipe> getRecipes(@AuthenticationPrincipal User user){
        return recipeService.getRecipes(user);
    }


    public RecipeController(RecipeService recipeService, AuthorizationService authorizationService) {
        this.recipeService = recipeService;
        this.authorizationService = authorizationService;
    }
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity addRecipe( @RequestPart("recipeBody") @Valid RecipeBody recipeBody,
                                     @RequestPart("image") MultipartFile imageFile) {
        User user = authorizationService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        try {
            byte[] imageData = imageFile.getBytes();
            recipeService.addRecipes(user, recipeBody, imageData);
            return ResponseEntity.ok().build();
        } catch (RecipeAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/{recipeId}/edit")
    public ResponseEntity updateRecipe(
            @PathVariable Long recipeId,
            @Valid @RequestBody RecipeBody updatedRecipeBody, @RequestParam("image") MultipartFile imageFile) {
        User user = authorizationService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            byte[] imageData = imageFile.getBytes();
            Recipe updatedRecipe = recipeService.updateRecipe(recipeId, updatedRecipeBody, imageData);
            return ResponseEntity.ok(updatedRecipe);
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RecipeAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{recipeId}/delete")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        try {
            recipeService.deleteRecipe(recipeId);
            return ResponseEntity.noContent().build();
        }
        catch (RecipeNotFoundException e ){
            return ResponseEntity.notFound().build();
        }
    }
}
