package com.fiuza.vacinnation_card_validation.infra.adapter;

import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.infra.model.UserModel;
import com.fiuza.vacinnation_card_validation.infra.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRepositoryImp userRepositoryImp;

    private UUID userId;
    private UserModel userModel;
    private User user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        userModel = new UserModel(
                userId,
                "12345678900",
                "João Silva",
                "1456879",
                "joao@teste.com",
                "1195107781",
                LocalDate.of(1995,12,07),
                Collections.emptyList()
        );

        user = new User(
                userId,
                "12345678900",
                "João Silva",
                "1456879",
                "joao@teste.com",
                "1195107781",
                LocalDate.of(1995,12,07)
        );
    }

    @Test
    void shouldFindUserByIdWhenExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userModel));

        Optional<User> result = userRepositoryImp.findUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
        assertEquals(user.getEmail(), result.get().getEmail());
        assertEquals(user.getName(), result.get().getName());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void shouldReturnEmptyWhenUserByIdDoesNotExist() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userRepositoryImp.findUserById(userId);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void shouldFindUserByEmailWhenExists() {
        String email = "joao@email.com";
        userModel.setEmail(email);

        when(userRepository.getUserModelByEmail(email)).thenReturn(Optional.of(userModel));

        Optional<User> result = userRepositoryImp.findUserEmail(email);

        assertTrue(result.isPresent());
        assertEquals(userModel.getId(), result.get().getId());
        assertEquals(email, result.get().getEmail());

        verify(userRepository, times(1)).getUserModelByEmail(email);
    }

    @Test
    void shouldReturnEmptyWhenUserByEmailDoesNotExist() {
        String email = "naoexiste@email.com";
        when(userRepository.getUserModelByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userRepositoryImp.findUserEmail(email);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).getUserModelByEmail(email);
    }

    @Test
    void shouldReturnTrueWhenUserExistsByEmail() {
        String email = "joao@email.com";
        when(userRepository.getUserModelByEmail(email)).thenReturn(Optional.of(userModel));

        boolean exists = userRepositoryImp.hasUser(email);

        assertTrue(exists);
        verify(userRepository, times(1)).getUserModelByEmail(email);
    }

    @Test
    void shouldReturnFalseWhenUserDoesNotExistByEmail() {
        String email = "naoexiste@email.com";
        when(userRepository.getUserModelByEmail(email)).thenReturn(Optional.empty());

        boolean exists = userRepositoryImp.hasUser(email);

        assertFalse(exists);
        verify(userRepository, times(1)).getUserModelByEmail(email);
    }
}

