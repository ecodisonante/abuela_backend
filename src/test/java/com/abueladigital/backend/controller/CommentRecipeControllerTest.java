package com.abueladigital.backend.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.abueladigital.backend.model.CommentRecipe;
import com.abueladigital.backend.model.CommentRecipeDTO;
import com.abueladigital.backend.model.Recipe;
import com.abueladigital.backend.service.CommentRecipeService;
import com.abueladigital.backend.service.RecipeService;

import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CommentRecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentRecipeService commentRecipeService;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debería obtener todos los comentarios")
    void testGetAllComments() throws Exception {
        // Datos de prueba
        CommentRecipe comment1 = new CommentRecipe();
        comment1.setId(1L);
        comment1.setContent("Comentario 1");

        CommentRecipe comment2 = new CommentRecipe();
        comment2.setId(2L);
        comment2.setContent("Comentario 2");

        List<CommentRecipe> comments = Arrays.asList(comment1, comment2);

        // Mock del CommentRecipeService
        when(commentRecipeService.getAllCommentRecipe()).thenReturn(comments);

        // Realizar la solicitud GET
        mockMvc.perform(get("/api/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(comments.size()))
                .andExpect(jsonPath("$[0].id").value(comment1.getId()))
                .andExpect(jsonPath("$[0].content").value(comment1.getContent()))
                .andExpect(jsonPath("$[1].id").value(comment2.getId()))
                .andExpect(jsonPath("$[1].content").value(comment2.getContent()));
    }

    @Test
    @DisplayName("Debería obtener un comentario por ID")
    void testGetCommentById() throws Exception {
        // Datos de prueba
        CommentRecipe comment = new CommentRecipe();
        comment.setId(1L);
        comment.setContent("Receta 1");

        // Mock del CommentRecipeService
        when(commentRecipeService.getCommentRecipeById(1L)).thenReturn(Optional.of(comment));

        // Realizar la solicitud GET
        mockMvc.perform(get("/api/comments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(comment.getId()))
                .andExpect(jsonPath("$.content").value(comment.getContent()));
    }

    @Test
    @DisplayName("Debería devolver 404 si el comentario no se encuentra")
    void testGetCommentByIdNotFound() throws Exception {
        // Mock del CommentRecipeService
        when(commentRecipeService.getCommentRecipeById(1L)).thenReturn(Optional.empty());

        // Realizar la solicitud GET
        mockMvc.perform(get("/api/comments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería crear un nuevo comentario")
    void testCreateCommentSuccess() throws Exception {
        // Datos de entrada
        CommentRecipeDTO commentInput = new CommentRecipeDTO();
        commentInput.setRecipeId(1L);
        commentInput.setContent("Nuevo comentario");

        Recipe relationalRecipe = new Recipe();
        relationalRecipe.setId(1L);
        when(recipeService.findById(1L)).thenReturn(Optional.of(relationalRecipe));

        // Datos de salida (receta guardada)
        CommentRecipe savedComment = new CommentRecipe();
        savedComment.setId(1L);
        savedComment.setContent("Nuevo comentario");

        // Mock del CommentRecipeService
        when(commentRecipeService.postCommentRecipe(any(CommentRecipe.class))).thenReturn(savedComment);

        // Realizar la solicitud POST
        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentInput)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(savedComment.getId()))
                .andExpect(jsonPath("$.content").value(savedComment.getContent()));
    }

    @Test
    @DisplayName("Debería crear un nuevo comentario")
    void testCreateCommentNotFound() throws Exception {
        // Datos de entrada
        CommentRecipeDTO commentInput = new CommentRecipeDTO();
        commentInput.setRecipeId(1L);
        commentInput.setContent("Nuevo comentario");

        // Mock del CommentRecipeService
        when(recipeService.findById(1L)).thenReturn(Optional.empty());

        // Realizar la solicitud POST
        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentInput)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería actualizar un comentario existente")
    void testUpdateCommentSuccess() throws Exception {
        // Datos de entrada
        CommentRecipeDTO commentInput = new CommentRecipeDTO();
        commentInput.setRecipeId(1L);
        commentInput.setContent("Comentario Actualizado");

        Recipe relationalRecipe = new Recipe();
        relationalRecipe.setId(1L);

        // Comentario existente
        CommentRecipe existingComment = new CommentRecipe();
        existingComment.setId(1L);
        existingComment.setContent("Comentario Original");

        // Comentario Actualizado
        CommentRecipe updatedComment = new CommentRecipe();
        updatedComment.setId(1L);
        updatedComment.setContent("Comentario Actualizado");

        // Mock del CommentRecipeService
        when(recipeService.findById(1L)).thenReturn(Optional.of(relationalRecipe));
        when(commentRecipeService.putCommentRecipe(anyLong(), any(CommentRecipe.class))).thenReturn(updatedComment);

        // Realizar la solicitud PUT
        mockMvc.perform(put("/api/comments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedComment.getId()))
                .andExpect(jsonPath("$.content").value(updatedComment.getContent()));
    }

    @Test
    @DisplayName("Debería devolver 404 al intentar actualizar un comentario de una receta inexistente")
    void testUpdateCommentNotFound1() throws Exception {
        // Datos de entrada
        CommentRecipeDTO commentInput = new CommentRecipeDTO();
        commentInput.setRecipeId(1L);
        commentInput.setContent("Comentario Actualizado");

        // Mock del CommentRecipeService
        when(recipeService.findById(1L)).thenReturn(Optional.empty());

        // Realizar la solicitud PUT
        mockMvc.perform(put("/api/comments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentInput)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería devolver 404 al intentar actualizar un comentario inexistente")
    void testUpdateCommentNotFound2() throws Exception {
        // Datos de entrada
        CommentRecipeDTO commentInput = new CommentRecipeDTO();
        commentInput.setRecipeId(1L);
        commentInput.setContent("Comentario Actualizado");

        Recipe relationalRecipe = new Recipe();
        relationalRecipe.setId(1L);

        // Comentario existente
        CommentRecipe existingComment = new CommentRecipe();
        existingComment.setId(1L);
        existingComment.setContent("Comentario Original");

        // Mock del CommentRecipeService
        when(recipeService.findById(1L)).thenReturn(Optional.of(relationalRecipe));
        when(commentRecipeService.putCommentRecipe(anyLong(), any(CommentRecipe.class))).thenReturn(null);

        // Realizar la solicitud PUT
        mockMvc.perform(put("/api/comments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentInput)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería eliminar un comentario existente")
    void testDeleteCommentSuccess() throws Exception {
        // Datos de entrada
        CommentRecipe comment = new CommentRecipe();
        comment.setId(1L);
        comment.setContent("Comentario Actualizado");

        // Mock del CommentRecipeService
        when(commentRecipeService.getCommentRecipeById(1L)).thenReturn(Optional.of(comment));
        doNothing().when(recipeService).delete(1L);

        // Realizar la solicitud DELETE
        mockMvc.perform(delete("/api/comments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería devolver 404 al intentar eliminar un comentario no existente")
    void testDeleteRecipeNotFound() throws Exception {
        // Mock del CommentRecipeService
        when(commentRecipeService.getCommentRecipeById(1L)).thenReturn(Optional.empty());

        // Realizar la solicitud DELETE
        mockMvc.perform(delete("/api/comments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
