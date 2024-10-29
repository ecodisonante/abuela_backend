package com.abueladigital.backend.repository;

import com.abueladigital.backend.model.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Long> {
    List<Instruction> findByRecipeId(Long recipeId);
}
