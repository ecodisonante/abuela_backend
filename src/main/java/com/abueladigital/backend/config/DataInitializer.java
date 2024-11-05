package com.abueladigital.backend.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.abueladigital.backend.model.Ingredient;
import com.abueladigital.backend.model.Instruction;
import com.abueladigital.backend.model.Recipe;
import com.abueladigital.backend.model.User;
import com.abueladigital.backend.repository.RecipeRepository;
import com.abueladigital.backend.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Crear usuarios
        User user1 = new User("Juan Pérez", "juan@test.com", passwordEncoder.encode("Secret123"));
        User user2 = new User("Ana García", "ana@test.com", passwordEncoder.encode("Secret123"));
        User user3 = new User("Carlos Gómez", "carlos@test.com", passwordEncoder.encode("Secret123"));

        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(user1, user2, user3));
        }

        if (recipeRepository.count() == 0) {
            createRecipes(user1, user2, user3);
        }
    }

    // Crear recetas de muestra
    private void createRecipes(User user1, User user2, User user3) {

        // Tacos
        Recipe tacos = new Recipe(
                "Tacos",
                "Tortillas de maíz con carne, salsa y guacamole.",
                4,
                "México",
                2,
                "tacos.jpg",
                4.5,
                user1);
        tacos.addIngredient(new Ingredient("Tortillas de maíz", "8 unidades"));
        tacos.addIngredient(new Ingredient("Carne molida", "500 gramos"));
        tacos.addIngredient(new Ingredient("Guacamole", "Al gusto"));
        tacos.addInstruction(new Instruction(1, "Cocinar la carne molida"));
        tacos.addInstruction(new Instruction(2, "Calentar las tortillas"));
        tacos.addInstruction(new Instruction(3, "Agregar la carne y guacamole a las tortillas"));

        // Pizza
        Recipe pizza = new Recipe(
                "Pizza",
                "Base de masa con salsa de tomate, queso y otros ingredientes.",
                6,
                "Italia",
                3,
                "pizza.jpg",
                4.8,
                user1);

        pizza.addIngredient(new Ingredient("Masa de pizza", "1 unidad"));
        pizza.addIngredient(new Ingredient("Salsa de tomate", "200 ml"));
        pizza.addIngredient(new Ingredient("Queso mozzarella", "300 gramos"));
        pizza.addInstruction(new Instruction(1, "Precalentar el horno a 220°C"));
        pizza.addInstruction(new Instruction(2, "Extender la salsa sobre la masa"));
        pizza.addInstruction(new Instruction(3, "Agregar queso y otros ingredientes"));
        pizza.addInstruction(new Instruction(4, "Hornear durante 15 minutos"));

        // Sushi
        Recipe sushi = new Recipe(
                "Sushi",
                "Arroz relleno con pescado crudo y vegetales, envuelto en algas.",
                4,
                "Japón",
                4,
                "sushi.jpg",
                4.7,
                user2);

        sushi.addIngredient(new Ingredient("Arroz para sushi", "400 gramos"));
        sushi.addIngredient(new Ingredient("Alga nori", "4 hojas"));
        sushi.addIngredient(new Ingredient("Salmón fresco", "300 gramos"));
        sushi.addInstruction(new Instruction(1, "Cocer el arroz"));
        sushi.addInstruction(new Instruction(2, "Colocar el arroz sobre el alga"));
        sushi.addInstruction(new Instruction(3, "Agregar salmón y enrollar"));

        // Enchiladas
        Recipe enchiladas = new Recipe(
                "Enchiladas",
                "Tortillas de maíz rellenas con carne, bañadas en salsa picante.",
                4,
                "México",
                3,
                "enchiladas.jpg",
                4.6,
                user2);

        enchiladas.addIngredient(new Ingredient("Tortillas de maíz", "8 unidades"));
        enchiladas.addIngredient(new Ingredient("Carne deshebrada", "400 gramos"));
        enchiladas.addIngredient(new Ingredient("Salsa roja", "200 ml"));
        enchiladas.addInstruction(new Instruction(1, "Cocinar la carne"));
        enchiladas.addInstruction(new Instruction(2, "Freír las tortillas"));
        enchiladas.addInstruction(new Instruction(3, "Cubrir con salsa y servir"));

        // Paella
        Recipe paella = new Recipe(
                "Paella",
                "Plato tradicional español con arroz, mariscos y azafrán.",
                6,
                "España",
                4,
                "paella.jpg",
                4.9,
                user3);

        paella.addIngredient(new Ingredient("Arroz", "500 gramos"));
        paella.addIngredient(new Ingredient("Mariscos", "500 gramos"));
        paella.addIngredient(new Ingredient("Azafrán", "1 pizca"));
        paella.addInstruction(new Instruction(1, "Sofreír los mariscos"));
        paella.addInstruction(new Instruction(2, "Agregar arroz y caldo"));
        paella.addInstruction(new Instruction(3, "Cocinar a fuego lento hasta que el arroz esté hecho"));

        // Ramen
        Recipe ramen = new Recipe(
                "Ramen",
                "Sopa japonesa con fideos, huevo y carne.",
                2,
                "Japón",
                4,
                "ramen.jpg",
                4.5,
                user3);

        ramen.addIngredient(new Ingredient("Fideos ramen", "200 gramos"));
        ramen.addIngredient(new Ingredient("Huevo", "2 unidades"));
        ramen.addIngredient(new Ingredient("Caldo", "500 ml"));
        ramen.addInstruction(new Instruction(1, "Cocer los fideos"));
        ramen.addInstruction(new Instruction(2, "Preparar el caldo"));
        ramen.addInstruction(new Instruction(3, "Agregar el huevo cocido y servir"));

        // Guardar todas las recetas
        recipeRepository.saveAll(List.of(tacos, pizza, sushi, enchiladas, paella, ramen));
    }
}
