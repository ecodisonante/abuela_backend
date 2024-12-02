package com.abueladigital.backend.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abueladigital.backend.model.CommentRecipe;
import com.abueladigital.backend.repository.CommentRecipeRepository;


@Service
public class CommentRecipeServiceImpl implements CommentRecipeService{

    @Autowired
    private CommentRecipeRepository commentRecipeRepository;

    @Override
    public List<CommentRecipe> getAllCommentRecipe() {
        return commentRecipeRepository.findAll();
    }

    @Override
    public Optional<CommentRecipe> getCommentRecipeById(Long id) {
        return commentRecipeRepository.findById(id);
    }
    
    @Override
    public CommentRecipe postCommentRecipe(CommentRecipe CommentRecipe)
    {
        return commentRecipeRepository.save(CommentRecipe);
    }

    @Override
    public CommentRecipe putCommentRecipe( Long id, CommentRecipe CommentRecipe)
    {
        if(commentRecipeRepository.existsById(id))
        {
            CommentRecipe.setId(id);
            return commentRecipeRepository.save(CommentRecipe);
        }
        else{
            return null;
        }
        
    }

    @Override
    public void deleteCommentRecipe(Long id)
    {
        commentRecipeRepository.deleteById(id);
    }
    
}
