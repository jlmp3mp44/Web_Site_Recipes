package com.kpi.recipes.model.dao;

import com.kpi.recipes.model.Image;
import org.springframework.data.repository.ListCrudRepository;

public interface ImageDAO extends ListCrudRepository<Image, Long> {
}
