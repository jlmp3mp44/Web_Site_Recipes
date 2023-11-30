package com.kpi.recipes.service;

import com.kpi.recipes.model.Category;
import com.kpi.recipes.model.dao.CategoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {
    private CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
    public List<Category> getCategories(){
        return categoryDAO.findAll();
    }
}
