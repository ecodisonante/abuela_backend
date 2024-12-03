package com.abueladigital.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRecipeDTO {

    private Long recipeId;
    private String content;

}
