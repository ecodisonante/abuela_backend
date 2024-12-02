package com.abueladigital.backend.repository;

import com.abueladigital.backend.model.CommentRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRecipeRepository extends JpaRepository<CommentRecipe, Long> {
}

