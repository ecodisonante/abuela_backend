package com.abueladigital.backend.service;

public interface JwtService {

    String generateToken(String username, String role);

    boolean validateToken(String token);

    String extractUsername(String token);

    String extractRole(String token);
    
}
