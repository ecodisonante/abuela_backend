package com.abueladigital.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abueladigital.backend.model.CommentRecipe;
import com.abueladigital.backend.service.CommentRecipeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentRecipeController {

    @Autowired
    private CommentRecipeService commentRecipeService;

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
    public ResponseEntity<CommentRecipe> createComment(@RequestBody CommentRecipe commentRecipe) {
        CommentRecipe createdComment = commentRecipeService.postCommentRecipe(commentRecipe);
        return ResponseEntity.status(201).body(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentRecipe> updateComment(
            @PathVariable Long id,
            @RequestBody CommentRecipe commentRecipe) {
        CommentRecipe updatedComment = commentRecipeService.putCommentRecipe(id, commentRecipe);
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