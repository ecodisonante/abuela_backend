package com.abueladigital.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InstructionTest {

    @Test
    void testInstructionGettersAndSetters() {
        // arrange
        String action = "Instruction action";
        var instruction = new Instruction();
        var recipe = new Recipe();

        // act
        instruction.setId(1L);
        instruction.setStep(1);
        instruction.setAction(action);
        instruction.setRecipe(recipe);

        // assert
        assertEquals(1L, instruction.getId());
        assertEquals(1, instruction.getStep());
        assertEquals(action, instruction.getAction());
        assertEquals(recipe, instruction.getRecipe());
    }

    @Test
    void testInstructionConstructor() {
        // arrange
        String action = "Instruction action";


        // act
        var instruction = new Instruction(1, action);

        // assert
        assertEquals(1, instruction.getStep());
        assertEquals(action, instruction.getAction());
    }
}
