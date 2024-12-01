package com.abueladigital.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl();
    }

    @Test
    void shouldGenerateValidToken() {
        // arrange
        String username = "testuser";
        String role = "ROLE_USER";

        // act
        String token = jwtService.generateToken(username, role);

        // assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(username, jwtService.extractUsername(token));
        assertEquals(role, jwtService.extractRole(token));
    }

    @Test
    void shouldValidateTokenSuccessfully() {
        // arrange
        String token = jwtService.generateToken("testuser", "ROLE_USER");
        String invalidToken = "invalid.token.value";
      
        // act & assert
        assertTrue(jwtService.validateToken(token));
        assertFalse(jwtService.validateToken(invalidToken));
    }

    @Test
    void shouldExtractUsernameFromToken() {
        // arrange
        String username = "testuser";
        String role = "ROLE_USER";

        // act
        String token = jwtService.generateToken(username, role);
        String extractedUsername = jwtService.extractUsername(token);

        // assert
        assertEquals(username, extractedUsername);
    }

    @Test
    void shouldExtractRoleFromToken() {
        // arrange
        String username = "testuser";
        String role = "ROLE_USER";

        // act
        String token = jwtService.generateToken(username, role);
        String extractedRole = jwtService.extractRole(token);

        // assert
        assertEquals(role, extractedRole);
    }
}
