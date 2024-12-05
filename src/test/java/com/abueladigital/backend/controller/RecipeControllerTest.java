package com.abueladigital.backend.controller;

import com.abueladigital.backend.model.Recipe;
import com.abueladigital.backend.service.RecipeService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debería obtener todas las recetas")
    void testGetAllRecipes() throws Exception {
        // Datos de prueba
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setName("Receta 1");

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setName("Receta 2");

        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        // Mock del RecipeService
        when(recipeService.findAll()).thenReturn(recipes);

        // Realizar la solicitud GET
        mockMvc.perform(get("/api/recipes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(recipes.size()))
                .andExpect(jsonPath("$[0].id").value(recipe1.getId()))
                .andExpect(jsonPath("$[0].name").value(recipe1.getName()))
                .andExpect(jsonPath("$[1].id").value(recipe2.getId()))
                .andExpect(jsonPath("$[1].name").value(recipe2.getName()));
    }

    @Test
    @DisplayName("Debería obtener una receta por ID")
    void testGetRecipeByIdSuccess() throws Exception {
        // Datos de prueba
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Receta 1");

        // Mock del RecipeService
        when(recipeService.findById(1L)).thenReturn(Optional.of(recipe));

        // Realizar la solicitud GET
        mockMvc.perform(get("/api/recipes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recipe.getId()))
                .andExpect(jsonPath("$.name").value(recipe.getName()));
    }

    @Test
    @DisplayName("Debería devolver 404 si la receta no se encuentra")
    void testGetRecipeByIdNotFound() throws Exception {
        // Mock del RecipeService
        when(recipeService.findById(1L)).thenReturn(Optional.empty());

        // Realizar la solicitud GET
        mockMvc.perform(get("/api/recipes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería buscar recetas por nombre")
    void testSearchRecipesByName() throws Exception {
        // Datos de prueba
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Receta de prueba");

        List<Recipe> recipes = Arrays.asList(recipe);

        // Mock del RecipeService
        when(recipeService.searchByName("prueba")).thenReturn(recipes);

        // Realizar la solicitud GET
        mockMvc.perform(get("/api/recipes/search")
                .param("name", "prueba")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(recipes.size()))
                .andExpect(jsonPath("$[0].id").value(recipe.getId()))
                .andExpect(jsonPath("$[0].name").value(recipe.getName()));
    }

    @Test
    @DisplayName("Debería crear una nueva receta")
    void testCreateRecipe() throws Exception {
        // Datos de entrada
        Recipe recipeInput = new Recipe();
        recipeInput.setName("Nueva Receta");

        // Datos de salida (receta guardada)
        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(1L);
        savedRecipe.setName("Nueva Receta");

        // Mock del RecipeService
        when(recipeService.save(any(Recipe.class))).thenReturn(savedRecipe);

        // Realizar la solicitud POST
        mockMvc.perform(post("/api/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeInput)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(savedRecipe.getId()))
                .andExpect(jsonPath("$.name").value(savedRecipe.getName()));
    }

    @Test
    @DisplayName("Debería actualizar una receta existente")
    void testUpdateRecipeSuccess() throws Exception {
        // Datos de entrada
        Recipe recipeInput = new Recipe();
        recipeInput.setName("Receta Actualizada");

        // Receta existente
        Recipe existingRecipe = new Recipe();
        existingRecipe.setId(1L);
        existingRecipe.setName("Receta Original");

        // Receta actualizada
        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setId(1L);
        updatedRecipe.setName("Receta Actualizada");

        // Mock del RecipeService
        when(recipeService.findById(1L)).thenReturn(Optional.of(existingRecipe));
        when(recipeService.save(any(Recipe.class))).thenReturn(updatedRecipe);

        // Realizar la solicitud PUT
        mockMvc.perform(put("/api/recipes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedRecipe.getId()))
                .andExpect(jsonPath("$.name").value(updatedRecipe.getName()));
    }

    @Test
    @DisplayName("Debería devolver 404 al intentar actualizar una receta no existente")
    void testUpdateRecipeNotFound() throws Exception {
        // Datos de entrada
        Recipe recipeInput = new Recipe();
        recipeInput.setName("Receta Actualizada");

        // Mock del RecipeService
        when(recipeService.findById(1L)).thenReturn(Optional.empty());

        // Realizar la solicitud PUT
        mockMvc.perform(put("/api/recipes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeInput)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería eliminar una receta existente")
    void testDeleteRecipeSuccess() throws Exception {
        // Receta existente
        Recipe existingRecipe = new Recipe();
        existingRecipe.setId(1L);
        existingRecipe.setName("Receta a Eliminar");

        // Mock del RecipeService
        when(recipeService.findById(1L)).thenReturn(Optional.of(existingRecipe));
        doNothing().when(recipeService).delete(1L);

        // Realizar la solicitud DELETE
        mockMvc.perform(delete("/api/recipes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Receta eliminada"));
    }

    @Test
    @DisplayName("Debería devolver 404 al intentar eliminar una receta no existente")
    void testDeleteRecipeNotFound() throws Exception {
        // Mock del RecipeService
        when(recipeService.findById(1L)).thenReturn(Optional.empty());

        // Realizar la solicitud DELETE
        mockMvc.perform(delete("/api/recipes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se encontró una receta con ese ID"));
    }

}
