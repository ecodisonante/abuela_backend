package com.abueladigital.backend.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
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

        private UserRepository userRepository;
        private RecipeRepository recipeRepository;
        private PasswordEncoder passwordEncoder;
        private final Environment env;

        @Autowired
        public DataInitializer(UserRepository userRepository, RecipeRepository recipeRepository,
                        PasswordEncoder passwordEncoder, Environment env) {
                this.userRepository = userRepository;
                this.recipeRepository = recipeRepository;
                this.passwordEncoder = passwordEncoder;
                this.env = env;
        }

        @Override
        public void run(String... args) throws Exception {

                var genericPass = env.getProperty("user.test.password");

                // Crear usuarios
                User user1 = new User("Juan Pérez", "juan@test.com", passwordEncoder.encode(genericPass));
                User user2 = new User("Ana García", "ana@test.com", passwordEncoder.encode(genericPass));
                User user3 = new User("Carlos Gómez", "carlos@test.com", passwordEncoder.encode(genericPass));

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
                Recipe tacos = new Recipe();
                tacos.setName("Tacos");
                tacos.setDescription("Tortillas de maíz con carne, salsa y guacamole.");
                tacos.setServings(4);
                tacos.setCountry("México");
                tacos.setDificulty(2);
                tacos.setImageUrl("tacos.jpg");
                tacos.setRate(4.5);
                tacos.setUser(user1);
                tacos.addIngredient(new Ingredient("Tortillas de maíz", "8 unidades"));
                tacos.addIngredient(new Ingredient("Carne molida", "480 gramos"));
                tacos.addIngredient(new Ingredient("Guacamole", "Al gusto"));
                tacos.addInstruction(new Instruction(1, "Cocinar la carne molida"));
                tacos.addInstruction(new Instruction(2, "Calentar las tortillas"));
                tacos.addInstruction(new Instruction(3, "Agregar la carne y guacamole a las tortillas"));

                // Pizza
                Recipe pizza = new Recipe();
                pizza.setName("Pizza");
                pizza.setDescription("Base de masa con salsa de tomate, queso y otros ingredientes.");
                pizza.setServings(6);
                pizza.setCountry("Italia");
                pizza.setDificulty(3);
                pizza.setImageUrl("pizza.jpg");
                pizza.setRate(4.8);
                pizza.setUser(user1);

                pizza.addIngredient(new Ingredient("Masa de pizza", "1 unidad"));
                pizza.addIngredient(new Ingredient("Salsa de tomate", "200 ml"));
                pizza.addIngredient(new Ingredient("Queso mozzarella", "300 gramos"));
                pizza.addInstruction(new Instruction(1, "Precalentar el horno a 220°C"));
                pizza.addInstruction(new Instruction(2, "Extender la salsa sobre la masa"));
                pizza.addInstruction(new Instruction(3, "Agregar queso y otros ingredientes"));
                pizza.addInstruction(new Instruction(4, "Hornear durante 15 minutos"));

                // Sushi
                Recipe sushi = new Recipe();
                sushi.setName("Sushi");
                sushi.setDescription("Arroz relleno con pescado crudo y vegetales, envuelto en algas.");
                sushi.setServings(4);
                sushi.setCountry("Japón");
                sushi.setDificulty(4);
                sushi.setImageUrl("sushi.jpg");
                sushi.setRate(4.7);
                sushi.setUser(user2);

                sushi.addIngredient(new Ingredient("Arroz para sushi", "400 gramos"));
                sushi.addIngredient(new Ingredient("Alga nori", "4 hojas"));
                sushi.addIngredient(new Ingredient("Salmón fresco", "300 gramos"));
                sushi.addInstruction(new Instruction(1, "Cocer el arroz"));
                sushi.addInstruction(new Instruction(2, "Colocar el arroz sobre el alga"));
                sushi.addInstruction(new Instruction(3, "Agregar salmón y enrollar"));

                // Enchiladas
                Recipe enchiladas = new Recipe();
                enchiladas.setName("Enchiladas");
                enchiladas.setDescription("Tortillas de maíz rellenas con carne, bañadas en salsa picante.");
                enchiladas.setServings(4);
                enchiladas.setCountry("México");
                enchiladas.setDificulty(3);
                enchiladas.setImageUrl("enchiladas.jpg");
                enchiladas.setRate(4.6);
                enchiladas.setUser(user2);

                enchiladas.addIngredient(new Ingredient("Tortillas de maíz", "8 unidades"));
                enchiladas.addIngredient(new Ingredient("Carne deshebrada", "400 gramos"));
                enchiladas.addIngredient(new Ingredient("Salsa roja", "200 ml"));
                enchiladas.addInstruction(new Instruction(1, "Cocinar la carne"));
                enchiladas.addInstruction(new Instruction(2, "Freír las tortillas"));
                enchiladas.addInstruction(new Instruction(3, "Cubrir con salsa y servir"));

                // Paella
                Recipe paella = new Recipe();
                paella.setName("Paella");
                paella.setDescription("Plato tradicional español con arroz, mariscos y azafrán.");
                paella.setServings(6);
                paella.setCountry("España");
                paella.setDificulty(4);
                paella.setImageUrl("paella.jpg");
                paella.setRate(4.9);
                paella.setUser(user3);

                paella.addIngredient(new Ingredient("Arroz", "450 gramos"));
                paella.addIngredient(new Ingredient("Mariscos", "500 gramos"));
                paella.addIngredient(new Ingredient("Azafrán", "1 pizca"));
                paella.addInstruction(new Instruction(1, "Sofreír los mariscos"));
                paella.addInstruction(new Instruction(2, "Agregar arroz y caldo"));
                paella.addInstruction(new Instruction(3, "Cocinar a fuego lento hasta que el arroz esté hecho"));

                // Ramen
                Recipe ramen = new Recipe();
                ramen.setName("Ramen");
                ramen.setDescription("Sopa japonesa con fideos, huevo y carne.");
                ramen.setServings(2);
                ramen.setCountry("Japón");
                ramen.setDificulty(4);
                ramen.setImageUrl("ramen.jpg");
                ramen.setRate(4.5);
                ramen.setUser(user3);

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
