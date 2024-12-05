package com.abueladigital.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CommentRecipeTest {

    @Test
    void testCommentRecipeGettersAndSetters() {
        // arrange
        String content = "CommentRecipe action";
        var comment = new CommentRecipe();
        var recipe = new Recipe();

        // act
        comment.setId(1L);
        comment.setContent(content);
        comment.setRecipe(recipe);

        // assert
        assertEquals(1L, comment.getId());
        assertEquals(content, comment.getContent());
        assertEquals(recipe, comment.getRecipe());
    }

    @Test
    void shouldSetCreatedDateOnPrePersist() {
        // act
        var comment = new CommentRecipe();
        comment.onCreate();

        // assert
        assertNotNull(comment.getCreated());
        assertTrue(comment.getCreated().isBefore(LocalDateTime.now())
                || comment.getCreated().isEqual(LocalDateTime.now()));
    }

}
