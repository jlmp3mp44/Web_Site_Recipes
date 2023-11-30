package com.kpi.recipes.model.dao;

import com.kpi.recipes.model.Category;
import org.springframework.data.repository.ListCrudRepository;

public interface CategoryDAO extends ListCrudRepository<Category, Long> {
}
