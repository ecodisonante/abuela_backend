package com.abueladigital.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeTest {

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
    }

    @Test
    void shouldSetCreatedDateOnPrePersist() {
        // act
        recipe.onCreate();

        // assert
        assertNotNull(recipe.getCreated());
        assertTrue(recipe.getCreated().isBefore(LocalDateTime.now())
                || recipe.getCreated().isEqual(LocalDateTime.now()));
    }

    @Test
    void shouldAddIngredientToRecipe() {
        // arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test ingredient");
        recipe.setIngredients(new ArrayList<>());

        // act
        recipe.addIngredient(ingredient);

        // assert
        assertEquals(1, recipe.getIngredients().size());
        assertEquals(recipe, ingredient.getRecipe());
    }

    @Test
    void shouldAddInstructionToRecipe() {
        // arrange
        var instruction = new Instruction();
        instruction.setStep(1);
        instruction.setAction("Test action");
        recipe.setInstructions(new ArrayList<>());

        // act
        recipe.addInstruction(instruction);

        // assert
        assertEquals(1, recipe.getInstructions().size());
        assertEquals(recipe, instruction.getRecipe());
    }

    @Test
    void shouldAddCommentToRecipe() {
        // arrange
        var comment = new CommentRecipe();
        comment.setContent("Comentario Nuevo");
        recipe.setComments(new ArrayList<>());

        // act
        recipe.addComment(comment);

        // assert
        assertEquals(1, recipe.getComments().size());
        assertEquals(recipe, comment.getRecipe());
    }
}
