package com.abueladigital.backend.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer servings;
    private String country;
    private Integer dificulty;
    private String imageUrl;
    private Double rate;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Instruction> instructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    private LocalDateTime created;

    //relacion con tabla comentario
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentRecipe> comments;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
    }

    // Método para agregar un ingrediente
    public void addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this); // Establece la relación
        this.ingredients.add(ingredient); // Añadir a la lista de ingredientes
    }

    // Método para agregar una instrucción
    public void addInstruction(Instruction instruction) {
        instruction.setRecipe(this); // Establece la relación
        this.instructions.add(instruction); // Añadir a la lista de instrucciones
    }

    public void addComment(CommentRecipe comment) {
        comment.setRecipe(this);
        this.comments.add(comment);
    }
}
