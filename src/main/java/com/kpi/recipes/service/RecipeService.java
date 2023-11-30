package com.kpi.recipes.service;

import com.kpi.recipes.model.Recipe;
import com.kpi.recipes.model.User;
import com.kpi.recipes.model.dao.RecipeDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private RecipeDAO recipeDAO;

    public RecipeService(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
    }
    public List<Recipe> getRecipes(User user){
        return recipeDAO.findByUser(user);
    }
}
