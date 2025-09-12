package com.fiuza.vacinnation_card_validation.core.usecases.user;

import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.core.gateway.UserGateway;
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
class GetUserByEmailUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private GetUserByEmailUseCase getUserByEmailUseCase;

    private User user;
    private String email;

    @BeforeEach
    void setUp() {
        email = "teste@email.com";
        user = new User();
        user.setId(UUID.randomUUID());
        user.setCpf("12345678900");
        user.setName("João da Silva");
        user.setCns("99999999999");
        user.setEmail(email);
        user.setPhone("11999999999");
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        user.setVaccinationList(Collections.emptyList());
    }

    @Test
    void shouldReturnUserWhenEmailExists() {
        when(userGateway.findUserEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = getUserByEmailUseCase.execute(email);

        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
        assertEquals(user.getEmail(), result.get().getEmail());
        assertEquals(user.getName(), result.get().getName());

        verify(userGateway, times(1)).findUserEmail(email);
    }

    @Test
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        when(userGateway.findUserEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = getUserByEmailUseCase.execute(email);

        assertFalse(result.isPresent());

        verify(userGateway, times(1)).findUserEmail(email);
    }
}
