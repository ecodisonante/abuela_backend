package com.abueladigital.backend.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abueladigital.backend.model.CommentRecipe;
import com.abueladigital.backend.repository.CommentRecipeRepository;


@Service
public class CommentRecipeServiceImpl implements CommentRecipeService{

    private CommentRecipeRepository commentRecipeRepository;
    
    @Autowired
    public CommentRecipeServiceImpl(CommentRecipeRepository commentRecipeRepository) {
        this.commentRecipeRepository = commentRecipeRepository;
    }

    @Override
    public List<CommentRecipe> getAllCommentRecipe() {
        return commentRecipeRepository.findAll();
    }

    @Override
    public Optional<CommentRecipe> getCommentRecipeById(Long id) {
        return commentRecipeRepository.findById(id);
    }
    
    @Override
    public CommentRecipe postCommentRecipe(CommentRecipe commentRecipe)
    {
        return commentRecipeRepository.save(commentRecipe);
    }

    @Override
    public CommentRecipe putCommentRecipe( Long id, CommentRecipe commentRecipe)
    {
        if(commentRecipeRepository.existsById(id))
        {
            commentRecipe.setId(id);
            return commentRecipeRepository.save(commentRecipe);
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
