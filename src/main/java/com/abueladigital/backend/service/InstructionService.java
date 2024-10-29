package com.abueladigital.backend.service;

import com.abueladigital.backend.model.Instruction;
import java.util.List;

public interface InstructionService {

    Instruction save(Instruction instruction);

    void delete(Long id);

    List<Instruction> findByRecipeId(Long recipeId);

}
