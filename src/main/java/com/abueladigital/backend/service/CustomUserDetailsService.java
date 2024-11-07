package com.abueladigital.backend.service;

import lombok.extern.java.Log;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.abueladigital.backend.model.User;
import com.abueladigital.backend.repository.UserRepository;

@Log
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Buscar usuario por email
        log.info("Cargando usuario con email: " + email);

        User usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    String message = String.format("Usuario con email %s no encontrado", email);
                    log.severe(message);
                    return new UsernameNotFoundException(message);
                });

        log.info("Usuario encontrado: " + usuario.getEmail());

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(),
                Collections.singletonList(authority));
    }
}