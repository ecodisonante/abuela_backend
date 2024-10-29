package com.abueladigital.backend.service;

import com.abueladigital.backend.model.Recipe;
import java.util.List;

public interface RecipeService {

    Recipe save(Recipe recipe);

    Recipe update(Recipe recipe);

    void delete(Long id);

    Recipe findById(Long id);

    List<Recipe> findAll();

    List<Recipe> searchByName(String name);
    
}
