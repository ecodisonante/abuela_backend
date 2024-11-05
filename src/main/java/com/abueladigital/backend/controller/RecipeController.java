package com.abueladigital.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abueladigital.backend.model.Recipe;
import com.abueladigital.backend.service.RecipeService;

import java.util.List;

/**
 * Controlador REST para gestionar las recetas.
 * Proporciona endpoints para obtener, buscar, crear, actualizar y eliminar
 * recetas.
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    /**
     * Obtener todas las recetas en una lista.
     *
     * @return ResponseEntity con la lista de todas las recetas y un estado HTTP 200
     *         (OK)
     */
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.findAll();
        return ResponseEntity.ok(recipes);
    }

    /**
     * Buscar y obtener una receta por su ID.
     *
     * @param id El ID de la receta a buscar
     * @return ResponseEntity con la receta encontrada y un estado HTTP 200 (OK),
     *         o un estado HTTP 404 (Not Found) si la receta no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.findById(id).orElse(null);

        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Buscar recetas por parte del nombre.
     *
     * @param name El nombre o parte del nombre de la receta para buscar
     * @return ResponseEntity con una lista de recetas que coinciden con el nombre
     *         y un estado HTTP 200 (OK)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipesByName(@RequestParam String name) {
        List<Recipe> recipes = recipeService.searchByName(name);
        return ResponseEntity.ok(recipes);
    }

    /**
     * Crear una nueva receta.
     *
     * @param recipe La receta a crear
     * @return ResponseEntity con la receta creada y un estado HTTP 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe savedRecipe = recipeService.save(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    /**
     * Actualizar una receta existente.
     *
     * @param id     El ID de la receta a actualizar
     * @param recipe Los datos de la receta actualizados
     * @return ResponseEntity con la receta actualizada y un estado HTTP 200 (OK),
     *         o un estado HTTP 404 (Not Found) si la receta no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe existingRecipe = recipeService.findById(id).orElse(null);

        if (existingRecipe != null) {
            recipe.setId(id);
            Recipe updatedRecipe = recipeService.update(recipe);
            return ResponseEntity.ok(updatedRecipe);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Eliminar una receta por su ID.
     *
     * @param id El ID de la receta a eliminar
     * @return ResponseEntity con un mensaje de éxito y un estado HTTP 200 (OK),
     *         o un estado HTTP 404 (Not Found) si la receta no se encuentra
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        Recipe existingRecipe = recipeService.findById(id).orElse(null);

        if (existingRecipe != null) {
            recipeService.delete(id);
            return ResponseEntity.ok("Receta eliminada");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una receta con ese ID");
        }
    }
}
