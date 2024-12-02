package com.abueladigital.backend.service;

import java.util.List;
import java.util.Optional;

import com.abueladigital.backend.model.CommentRecipe;

public interface CommentRecipeService {
    List<CommentRecipe>getAllCommentRecipe();
    Optional<CommentRecipe>getCommentRecipeById(Long id);
    CommentRecipe postCommentRecipe(CommentRecipe comment);
    CommentRecipe putCommentRecipe(Long id, CommentRecipe comment);
    void deleteCommentRecipe(Long id); 
}