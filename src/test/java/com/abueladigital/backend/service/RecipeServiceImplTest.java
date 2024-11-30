package com.abueladigital.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.abueladigital.backend.model.Recipe;
import com.abueladigital.backend.model.User;
import com.abueladigital.backend.repository.RecipeRepository;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    void testSave() {
        // arrange
        User user = new User("Test User", "user@test.com", "testPass");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                null);

        Recipe recipe = new Recipe();
        recipe.setName("Tacos");
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions(new ArrayList<>());

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userService.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        // act
        Recipe savedRecipe = recipeService.save(recipe);

        // assert
        assertNotNull(savedRecipe);
        assertEquals("Tacos", savedRecipe.getName());
        verify(recipeRepository).save(recipe);
        verify(userService).findByEmail(user.getEmail());

        // Limpiar el contexto de seguridad para evitar interferencias en otras pruebas
        SecurityContextHolder.clearContext();
    }

    @Test
    void testUpdate() {
        // arrange
        User user = new User("Test User", "user@test.com", "testPass");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                null);

        Recipe recipe = new Recipe();
        recipe.setName("Tacos");
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions(new ArrayList<>());

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userService.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        // act
        Recipe savedRecipe = recipeService.update(recipe);

        // assert
        assertNotNull(savedRecipe);
        assertEquals("Tacos", savedRecipe.getName());
        verify(recipeRepository).save(recipe);
        verify(userService).findByEmail(user.getEmail());

        // Limpiar el contexto de seguridad para evitar interferencias en otras pruebas
        SecurityContextHolder.clearContext();
    }


    @Test
    void testDelete() {
        // act
        recipeService.delete(1L);
        // assert
        verify(recipeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindById() {
        // arrange
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Paella");

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        // act
        Optional<Recipe> foundRecipe = recipeService.findById(1L);

        // assert
        assertTrue(foundRecipe.isPresent());
        assertEquals("Paella", foundRecipe.get().getName());
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        // arrange
        Recipe recipe1 = new Recipe();
        recipe1.setName("Paella");

        Recipe recipe2 = new Recipe();
        recipe2.setName("Tacos");

        when(recipeRepository.findAll()).thenReturn(List.of(recipe1, recipe2));

        // act
        List<Recipe> recipes = recipeService.findAll();

        // assert
        assertEquals(2, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testSearchByName() {
        // arrange
        Recipe recipe = new Recipe();
        recipe.setName("Paella");

        when(recipeRepository.findByNameContainingIgnoreCase("paella")).thenReturn(List.of(recipe));

        // act
        List<Recipe> recipes = recipeService.searchByName("paella");

        // assert
        assertEquals(1, recipes.size());
        assertEquals("Paella", recipes.get(0).getName());
        verify(recipeRepository, times(1)).findByNameContainingIgnoreCase("paella");
    }

}
