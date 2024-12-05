package com.abueladigital.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IngredientTest {

    @Test
    void testIngredientGettersAndSetters() {
        // arrange
        String name = "Ingredient name";
        String ammount = "Ingredient ammount";
        var ingredient = new Ingredient();
        var recipe = new Recipe();

        // act
        ingredient.setId(1L);
        ingredient.setName(name);
        ingredient.setAmmount(ammount);
        ingredient.setRecipe(recipe);

        // assert
        assertEquals(1L, ingredient.getId());
        assertEquals(name, ingredient.getName());
        assertEquals(ammount, ingredient.getAmmount());
        assertEquals(recipe, ingredient.getRecipe());
    }

    @Test
    void testIngredientConstructor() {
        // arrange
        String name = "Ingredient name";
        String ammount = "Ingredient ammount";

        // act
        var ingredient = new Ingredient(name, ammount);

        // assert
        assertEquals(name, ingredient.getName());
        assertEquals(ammount, ingredient.getAmmount());
    }
}
