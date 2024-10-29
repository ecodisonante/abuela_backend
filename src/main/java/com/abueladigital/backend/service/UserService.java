package com.abueladigital.backend.service;

import com.abueladigital.backend.model.User;
import java.util.List;

public interface UserService {

    User save(User user);

    User update(User user);

    void delete(Long id);

    User findById(Long id);

    List<User> findAll();

    User findByEmail(String email);

}
