package com.fiuza.vacinnation_card_validation.core.usecases.vaccination;

import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;
import com.fiuza.vacinnation_card_validation.core.enums.Type;
import com.fiuza.vacinnation_card_validation.core.exceptions.NotFoundException;
import com.fiuza.vacinnation_card_validation.core.gateway.UserGateway;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateVaccinationUseCaseTest {

    @Mock
    private VaccinationGateway vaccinationGateway;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private CreateVaccinationUseCase createVaccinationUseCase;

    private UUID userId;
    private UUID vaccinationId;
    private User user;
    private Vaccination vaccination;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        vaccinationId = UUID.randomUUID();

        user = new User(userId);

        vaccination = new Vaccination(
                vaccinationId,
                "Vacina Gripe",
                LocalDate.of(2024, 9, 8),
                Type.PRIMEIRA_DOSE,
                null
        );
    }

    @Test
    void shouldCreateVaccinationWhenUserExists() {
        when(userGateway.findUserById(userId)).thenReturn(Optional.of(user));
        when(vaccinationGateway.create(any(Vaccination.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Vaccination result = createVaccinationUseCase.execute(vaccination, userId);

        assertNotNull(result);
        assertEquals(vaccination.getId(), result.getId());
        assertEquals(vaccination.getName(), result.getName());
        assertEquals(vaccination.getDate(), result.getDate());
        assertEquals(vaccination.getType(), result.getType());
        assertEquals(user, result.getUser());

        verify(userGateway, times(1)).findUserById(userId);
        verify(vaccinationGateway, times(1)).create(any(Vaccination.class));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserDoesNotExist() {
        when(userGateway.findUserById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> createVaccinationUseCase.execute(vaccination, userId)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());

        verify(vaccinationGateway, never()).create(any(Vaccination.class));
    }
}

