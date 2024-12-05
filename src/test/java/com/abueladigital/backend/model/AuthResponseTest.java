package com.abueladigital.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AuthResponseTest {

    @Test
    void testAuthResponseConstructor() {
        // arrange & act
        var authResponse = new AuthResponse("testToken");

        // assert
        assertEquals(AuthResponse.class, authResponse.getClass());
    }
}
