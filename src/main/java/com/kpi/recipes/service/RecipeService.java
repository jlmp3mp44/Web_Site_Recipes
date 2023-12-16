package com.kpi.recipes.service;

import com.kpi.recipes.api.exception.RecipeAlreadyExistException;
import com.kpi.recipes.api.exception.RecipeNotFoundException;
import com.kpi.recipes.api.model.RecipeBody;
import com.kpi.recipes.model.Category;
import com.kpi.recipes.model.Recipe;
import com.kpi.recipes.model.User;
import com.kpi.recipes.model.dao.CategoryDAO;
import com.kpi.recipes.model.dao.RecipeDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private RecipeDAO recipeDAO;

    public RecipeService(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
    }
    public List<Recipe> getRecipes(User user){
        return recipeDAO.findByUser(user);
    }
    public Recipe addRecipes(User user, RecipeBody recipeBody) throws RecipeAlreadyExistException {
        if (recipeDAO.findByTitleIgnoreCase(recipeBody.getTitle()).isPresent()) {
            throw new RecipeAlreadyExistException();
        }

        Recipe recipe =  new Recipe();
        recipe.setUser(user);
        recipe.setTitle(recipeBody.getTitle());
        recipe.setMenuRecipe(recipeBody.getMenuRecipe());
        recipe.setRecipeIngredients(recipeBody.getIngredientRecipes());
        recipe.setCalorie(recipeBody.getCalorie());
        recipe.setCategory(recipeBody.getCategory());
        recipe.setDescription(recipeBody.getDescription());
        recipe.setTime(recipeBody.getTime());
        recipe =  recipeDAO.save(recipe);
        return recipe;
    }
    public Recipe updateRecipe(Long recipeId, RecipeBody updatedRecipeBody) throws RecipeNotFoundException, RecipeAlreadyExistException {
        Optional<Recipe> optionalRecipe = recipeDAO.findById(recipeId);

        if (optionalRecipe.isPresent()) {
            Recipe existingRecipe = optionalRecipe.get();

            // Check if the title is being updated to a title that already exists
            if (!existingRecipe.getTitle().equalsIgnoreCase(updatedRecipeBody.getTitle())
                    && recipeDAO.findByTitleIgnoreCase(updatedRecipeBody.getTitle()).isPresent()) {
                throw new RecipeAlreadyExistException("Recipe with the same title already exists.");
            }

            // Update the recipe fields with the new values
            existingRecipe.setTitle(updatedRecipeBody.getTitle());
            existingRecipe.setDescription(updatedRecipeBody.getDescription());
            existingRecipe.setTime(updatedRecipeBody.getTime());
            existingRecipe.setCalorie(updatedRecipeBody.getCalorie());
            existingRecipe.setCategory(updatedRecipeBody.getCategory());
            existingRecipe.setRecipeIngredients(updatedRecipeBody.getIngredientRecipes());
            existingRecipe.setMenuRecipe(updatedRecipeBody.getMenuRecipe());

            // Save the updated recipe
            return recipeDAO.save(existingRecipe);
        } else {
            throw new RecipeNotFoundException("Recipe not found with id: " + recipeId);
        }
    }
}

