package com.abueladigital.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.abueladigital.backend.model.User;
import com.abueladigital.backend.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void testDelete() {
        // act
        service.delete(1L);
        // assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindAll() {
        // arrange
        User user1 = new User("Test User", "user@test.com", "testPass");
        User user2 = new User("Test User", "user@test.com", "testPass");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        // act
        List<User> usuarios = service.findAll();
        // assert
        assertEquals(2, usuarios.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindByEmail() {
        // arrange
        User user = new User("Test User", "user@test.com", "testPass");
        user.setId(1L);
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        // act
        Optional<User> foundUser = service.findByEmail("user@test.com");
        // assert
        assertTrue(foundUser.isPresent());
        assertEquals("Test User", foundUser.get().getName());
        verify(userRepository, times(1)).findByEmail("user@test.com");
    }

    @Test
    void testFindById() {
        // arrange
        User user = new User("Test User", "user@test.com", "testPass");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // act
        Optional<User> foundUser = service.findById(1L);
        // assert
        assertTrue(foundUser.isPresent());
        assertEquals("Test User", foundUser.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        // arrange
        User user = new User("Test User", "user@test.com", "testPass");
        when(userRepository.save(user)).thenReturn(user);

        // act
        var result = service.save(user);

        // assert
        verify(userRepository, times(1)).save(user);
        assertEquals(user.getName(), result.getName());
    }

    @Test
    void testUpdate() {
        // arrange
        User user = new User("Test User", "user@test.com", "testPass");
        when(userRepository.save(user)).thenReturn(user);

        // act
        var result = service.update(user);

        // assert
        verify(userRepository, times(1)).save(user);
        assertEquals("Test User", result.getName());
    }
}
