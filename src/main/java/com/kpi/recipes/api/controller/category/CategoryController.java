package com.kpi.recipes.api.controller.category;

import com.kpi.recipes.api.exception.*;
import com.kpi.recipes.api.model.CategoryBody;
import com.kpi.recipes.api.model.RecipeBody;
import com.kpi.recipes.api.model.RegistrationBody;
import com.kpi.recipes.model.Category;
import com.kpi.recipes.model.Recipe;
import com.kpi.recipes.model.User;
import com.kpi.recipes.service.AuthorizationService;
import com.kpi.recipes.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    private AuthorizationService authorizationService;

    public CategoryController(CategoryService categoryService, AuthorizationService authorizationService) {
        this.categoryService = categoryService;
        this.authorizationService =  authorizationService;
    }

    @GetMapping
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }

    @PostMapping("/add")
    public ResponseEntity registerUser(@Valid @RequestBody CategoryBody categoryBody) {
        try {
            categoryService.addCategory(categoryBody);
            return ResponseEntity.ok().build();
        } catch (CategoryAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PutMapping("/{categoryId}/edit")
    public ResponseEntity updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryBody updatedCategoryBody) {
        User user = authorizationService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, updatedCategoryBody);
            return ResponseEntity.ok(updatedCategory);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (CategoryAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @DeleteMapping("/{categoryId}/delete")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.noContent().build();
        }
        catch (CategoryNotFoundException e ){
            return ResponseEntity.notFound().build();
        }
    }
}
