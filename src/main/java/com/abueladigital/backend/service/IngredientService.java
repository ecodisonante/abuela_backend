package com.abueladigital.backend.service;

import com.abueladigital.backend.model.Ingredient;
import java.util.List;

public interface IngredientService {

    Ingredient save(Ingredient ingredient);

    void delete(Long id);

    List<Ingredient> findByRecipeId(Long recipeId);

}
