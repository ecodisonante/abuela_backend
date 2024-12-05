package com.abueladigital.backend.service;

import com.abueladigital.backend.model.CommentRecipe;
import com.abueladigital.backend.repository.CommentRecipeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CommentRecipeServiceImplTest {

    private CommentRecipeRepository commentRecipeRepository;
    private CommentRecipeServiceImpl commentRecipeService;

    @BeforeEach
    void setUp() {
        commentRecipeRepository = mock(CommentRecipeRepository.class);
        commentRecipeService = new CommentRecipeServiceImpl(commentRecipeRepository);
    }

    @Test
    @DisplayName("Debería obtener todos los comentarios de recetas")
    void testGetAllCommentRecipe() {
        // Datos de prueba
        CommentRecipe comment1 = new CommentRecipe();
        comment1.setId(1L);
        comment1.setContent("Comentario 1");

        CommentRecipe comment2 = new CommentRecipe();
        comment2.setId(2L);
        comment2.setContent("Comentario 2");

        List<CommentRecipe> comments = Arrays.asList(comment1, comment2);

        // Mock del repositorio
        when(commentRecipeRepository.findAll()).thenReturn(comments);

        // Llamada al método a probar
        List<CommentRecipe> result = commentRecipeService.getAllCommentRecipe();

        // Verificaciones
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Comentario 1", result.get(0).getContent());
        assertEquals("Comentario 2", result.get(1).getContent());

        // Verificar que el método del repositorio fue llamado una vez
        verify(commentRecipeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener un comentario por su ID")
    void testGetCommentRecipeByIdSuccess() {
        // Datos de prueba
        CommentRecipe comment = new CommentRecipe();
        comment.setId(1L);
        comment.setContent("Comentario de prueba");

        // Mock del repositorio
        when(commentRecipeRepository.findById(1L)).thenReturn(Optional.of(comment));

        // Llamada al método a probar
        Optional<CommentRecipe> result = commentRecipeService.getCommentRecipeById(1L);

        // Verificaciones
        assertTrue(result.isPresent());
        assertEquals("Comentario de prueba", result.get().getContent());

        // Verificar que el método del repositorio fue llamado una vez
        verify(commentRecipeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería devolver vacío si el comentario no existe")
    void testGetCommentRecipeByIdNotFound() {
        // Mock del repositorio
        when(commentRecipeRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamada al método a probar
        Optional<CommentRecipe> result = commentRecipeService.getCommentRecipeById(1L);

        // Verificaciones
        assertFalse(result.isPresent());

        // Verificar que el método del repositorio fue llamado una vez
        verify(commentRecipeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería crear un nuevo comentario")
    void testPostCommentRecipe() {
        // Datos de entrada
        CommentRecipe commentInput = new CommentRecipe();
        commentInput.setContent("Nuevo comentario");

        // Datos de salida
        CommentRecipe savedComment = new CommentRecipe();
        savedComment.setId(1L);
        savedComment.setContent("Nuevo comentario");

        // Mock del repositorio
        when(commentRecipeRepository.save(any(CommentRecipe.class))).thenReturn(savedComment);

        // Llamada al método a probar
        CommentRecipe result = commentRecipeService.postCommentRecipe(commentInput);

        // Verificaciones
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Nuevo comentario", result.getContent());

        // Verificar que el método del repositorio fue llamado una vez
        verify(commentRecipeRepository, times(1)).save(commentInput);
    }

    @Test
    @DisplayName("Debería actualizar un comentario existente")
    void testPutCommentRecipeSuccess() {
        // Datos de entrada
        CommentRecipe commentInput = new CommentRecipe();
        commentInput.setContent("Comentario Actualizado");

        // Comentario existente
        CommentRecipe existingComment = new CommentRecipe();
        existingComment.setId(1L);
        existingComment.setContent("Comentario Original");

        // Mock del repositorio
        when(commentRecipeRepository.existsById(1L)).thenReturn(true);
        when(commentRecipeRepository.save(any(CommentRecipe.class))).thenReturn(commentInput);

        // Llamada al método a probar
        CommentRecipe result = commentRecipeService.putCommentRecipe(1L, commentInput);

        // Verificaciones
        assertNotNull(result);
        assertEquals("Comentario Actualizado", result.getContent());

        // Verificar que los métodos del repositorio fueron llamados
        verify(commentRecipeRepository, times(1)).existsById(1L);
        verify(commentRecipeRepository, times(1)).save(commentInput);
    }

    @Test
    @DisplayName("Debería devolver null al intentar actualizar un comentario inexistente")
    void testPutCommentRecipeNotFound() {
        // Datos de entrada
        CommentRecipe commentInput = new CommentRecipe();
        commentInput.setContent("Comentario Actualizado");

        // Mock del repositorio
        when(commentRecipeRepository.existsById(1L)).thenReturn(false);

        // Llamada al método a probar
        CommentRecipe result = commentRecipeService.putCommentRecipe(1L, commentInput);

        // Verificaciones
        assertNull(result);

        // Verificar que se llamó a existsById y no a save
        verify(commentRecipeRepository, times(1)).existsById(1L);
        verify(commentRecipeRepository, never()).save(any(CommentRecipe.class));
    }

    @Test
    @DisplayName("Debería eliminar un comentario por su ID")
    void testDeleteCommentRecipe() {
        // No necesitamos datos de prueba específicos para este caso

        // Llamada al método a probar
        commentRecipeService.deleteCommentRecipe(1L);

        // Verificar que el método del repositorio fue llamado una vez
        verify(commentRecipeRepository, times(1)).deleteById(1L);
    }

}
