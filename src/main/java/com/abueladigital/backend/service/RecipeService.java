package com.abueladigital.backend.service;

import com.abueladigital.backend.model.Recipe;
import java.util.List;
import java.util.Optional;

public interface RecipeService {

    Recipe save(Recipe recipe);

    Recipe update(Recipe recipe);

    void delete(Long id);

    Optional<Recipe> findById(Long id);

    List<Recipe> findAll();

    List<Recipe> searchByName(String name);

}
