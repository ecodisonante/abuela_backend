package com.abueladigital.backend.service;

import com.abueladigital.backend.model.Recipe;
import com.abueladigital.backend.model.User;
import com.abueladigital.backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserService userService;

    @Override
    public Recipe save(Recipe recipe) {
        this.addRecipeReferences(recipe);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe update(Recipe recipe) {
        this.addRecipeReferences(recipe);
        return recipeRepository.save(recipe);
    }

    @Override
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> searchByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCase(name);
    }

    private void addRecipeReferences(Recipe recipe) {
        // Agregar referencia de user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByEmail(username).get();
        recipe.setUser(user);

        // Agregar referencia de recipe en listas
        recipe.getInstructions().forEach(x -> x.setRecipe(recipe));
        recipe.getIngredients().forEach(x -> x.setRecipe(recipe));
    }
}
