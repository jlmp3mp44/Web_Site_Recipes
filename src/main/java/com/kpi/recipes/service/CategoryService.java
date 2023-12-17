package com.kpi.recipes.service;

import com.kpi.recipes.api.exception.CategoryAlreadyExistException;
import com.kpi.recipes.api.exception.CategoryNotFoundException;
import com.kpi.recipes.api.exception.RecipeNotFoundException;
import com.kpi.recipes.api.model.CategoryBody;
import com.kpi.recipes.model.Category;
import com.kpi.recipes.model.Recipe;
import com.kpi.recipes.model.dao.CategoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
    public List<Category> getCategories(){
        return categoryDAO.findAll();
    }
    public Category addCategory(CategoryBody categoryBody) throws CategoryAlreadyExistException {
        if (categoryDAO.findAllByName(categoryBody.getName()).isPresent()) {
            throw new CategoryAlreadyExistException();
        }

        Category category =  new Category();
        category.setName(categoryBody.getName());
        category.setDescription(categoryBody.getDescription());
        category.setRecipes(categoryBody.getRecipes());
        category =  categoryDAO.save(category);
        return category;
    }
    public Category updateCategory(Long categoryId, CategoryBody updatedCategoryBody) throws CategoryNotFoundException, CategoryAlreadyExistException {
        Optional<Category> optionalCategory = categoryDAO.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();

            // Check if the title is being updated to a title that already exists
            if (!existingCategory.getName().equalsIgnoreCase(updatedCategoryBody.getName())
                    && categoryDAO.findByNameIgnoreCase(updatedCategoryBody.getName()).isPresent()) {
                throw new CategoryAlreadyExistException("Recipe with the same title already exists.");
            }

            // Update the recipe fields with the new values
            existingCategory.setName(updatedCategoryBody.getName());
            existingCategory.setDescription(updatedCategoryBody.getDescription());

            // Save the updated recipe
            return categoryDAO.save(existingCategory);
        } else {
            throw new CategoryNotFoundException("Recipe not found with id: " + categoryId);
        }
    }
    public void deleteCategory(Long categoryId) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryDAO.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            categoryDAO.delete(category);
        } else {
            throw new CategoryNotFoundException("Category not found with id: " + categoryId);
        }
    }
}
