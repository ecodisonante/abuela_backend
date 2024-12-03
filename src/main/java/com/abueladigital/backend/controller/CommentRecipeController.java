package com.abueladigital.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abueladigital.backend.model.CommentRecipe;
import com.abueladigital.backend.model.CommentRecipeDTO;
import com.abueladigital.backend.service.CommentRecipeService;
import com.abueladigital.backend.service.RecipeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentRecipeController {

    private CommentRecipeService commentRecipeService;
    private RecipeService recipeService;

    @Autowired
    public CommentRecipeController(CommentRecipeService commentRecipeService, RecipeService recipeService) {
        this.commentRecipeService = commentRecipeService;
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<CommentRecipe>> getAllComments() {
        List<CommentRecipe> comments = commentRecipeService.getAllCommentRecipe();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentRecipe> getCommentById(@PathVariable Long id) {
        Optional<CommentRecipe> comment = commentRecipeService.getCommentRecipeById(id);
        return comment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CommentRecipe> createComment(@RequestBody CommentRecipeDTO request) {
        CommentRecipe comment = new CommentRecipe();

        // buscar receta
        var recipe = recipeService.findById(request.getRecipeId());

        // insertar comentario si receeta existe
        if (recipe.isPresent()) {
            comment.setContent(request.getContent());
            comment.setRecipe(recipe.get());
            
            CommentRecipe createdComment = commentRecipeService.postCommentRecipe(comment);
            return ResponseEntity.status(201).body(createdComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentRecipe> updateComment(
            @PathVariable Long id,
            @RequestBody CommentRecipeDTO request) {

        // Convertir request a CommentRecipe
        CommentRecipe comment = new CommentRecipe();
        var recipe = recipeService.findById(request.getRecipeId());
        if (recipe.isPresent()) {
            comment.setContent(request.getContent());
            comment.setRecipe(recipe.get());
        } else {
            return ResponseEntity.notFound().build();
        }

        CommentRecipe updatedComment = commentRecipeService.putCommentRecipe(id, comment);
        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if (commentRecipeService.getCommentRecipeById(id).isPresent()) {
            commentRecipeService.deleteCommentRecipe(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}