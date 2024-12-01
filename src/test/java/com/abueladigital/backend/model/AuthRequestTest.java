package com.abueladigital.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AuthRequestTest {

    @Test
    void testAuthRequestGettersAndSetters() {
        // arrange
        var authRequest = new AuthRequest();

        // act
        authRequest.setEmail("email@test.com");
        authRequest.setPassword("Secret123");

        // assert
        assertEquals(AuthRequest.class, authRequest.getClass());
        assertEquals("email@test.com", authRequest.getEmail());
        assertEquals("Secret123", authRequest.getPassword());
    }
}
