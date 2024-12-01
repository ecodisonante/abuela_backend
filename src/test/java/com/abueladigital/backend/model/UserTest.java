package com.abueladigital.backend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void shouldSetValuesThroughConstructor() {
        // Arrange & Act: Crear un usuario con el constructor personalizado
        User user = new User("John Doe", "john@example.com", "securePassword");

        // Assert: Verificar que los valores se asignaron correctamente
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("securePassword", user.getPassword());
    }

    @Test
    void shouldSetCreatedDateOnPrePersist() {
        // Act: Simular el evento de persistencia
        user.onCreate();

        // Assert: Validar que la fecha de creación no sea nula y sea razonable
        assertNotNull(user.getCreated());
        assertTrue(user.getCreated().isBefore(LocalDateTime.now()) || user.getCreated().isEqual(LocalDateTime.now()));
    }

    @Test
    void shouldSetUpdatedDateOnPreUpdate() {
        // Act: Simular el evento de actualización
        user.onUpdate();

        // Assert: Validar que la fecha de actualización no sea nula y sea razonable
        assertNotNull(user.getUpdated());
        assertTrue(user.getUpdated().isBefore(LocalDateTime.now()) || user.getUpdated().isEqual(LocalDateTime.now()));
    }

    @Test
    void shouldHandleDefaultValues() {
        // Act: Crear una nueva instancia de User
        User newUser = new User();

        // Assert: Validar que las colecciones y valores no sean nulos
        assertNull(newUser.getId());
        assertNull(newUser.getName());
        assertNull(newUser.getEmail());
        assertNull(newUser.getPassword());
        assertNull(newUser.getCreated());
        assertNull(newUser.getUpdated());
        assertNull(newUser.getRecipes());
    }
}
