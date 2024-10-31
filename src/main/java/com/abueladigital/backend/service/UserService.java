package com.abueladigital.backend.service;

import com.abueladigital.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    User update(User user);

    void delete(Long id);

}
