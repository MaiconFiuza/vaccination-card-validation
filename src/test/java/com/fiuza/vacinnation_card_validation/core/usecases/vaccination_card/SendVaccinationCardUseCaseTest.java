package com.fiuza.vacinnation_card_validation.core.usecases.vaccination_card;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.enums.Status;
import com.fiuza.vacinnation_card_validation.core.exceptions.AlreadyExistException;
import com.fiuza.vacinnation_card_validation.core.exceptions.NotFoundException;
import com.fiuza.vacinnation_card_validation.core.gateway.UserGateway;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;
import com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card.SendVaccinationCardUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendVaccinationCardUseCaseTest {

    @Mock
    private VaccinationCardGateway vaccinationCardGateway;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private SendVaccinationCardUseCase sendVaccinationCardUseCase;

    private VaccinationCard vaccinationCard;

    @BeforeEach
    void setUp() {
        vaccinationCard = new VaccinationCard();
        vaccinationCard.setId(UUID.randomUUID());
        vaccinationCard.setEmail("teste@email.com");
        vaccinationCard.setFrontCard("front".getBytes(StandardCharsets.UTF_8));
        vaccinationCard.setBackCard("back".getBytes(StandardCharsets.UTF_8));
        vaccinationCard.setStatus(Status.RECEIVED);
        vaccinationCard.setMessage("Cartão enviado com sucesso");
    }

    @Test
    void shouldCreateVaccinationCardWhenUserExistsAndNoCardSaved() {
        when(userGateway.hasUser(vaccinationCard.getEmail())).thenReturn(true);
        when(vaccinationCardGateway.findByEmail(vaccinationCard.getEmail())).thenReturn(Optional.empty());
        when(vaccinationCardGateway.create(vaccinationCard)).thenReturn(vaccinationCard);

        VaccinationCard result = sendVaccinationCardUseCase.execute(vaccinationCard);

        assertNotNull(result);
        assertEquals(vaccinationCard.getId(), result.getId());
        assertEquals(vaccinationCard.getEmail(), result.getEmail());
        assertArrayEquals(vaccinationCard.getFrontCard(), result.getFrontCard());
        assertArrayEquals(vaccinationCard.getBackCard(), result.getBackCard());
        assertEquals(vaccinationCard.getStatus(), result.getStatus());
        assertEquals(vaccinationCard.getMessage(), result.getMessage());

        verify(userGateway, times(1)).hasUser(vaccinationCard.getEmail());
        verify(vaccinationCardGateway, times(1)).findByEmail(vaccinationCard.getEmail());
        verify(vaccinationCardGateway, times(1)).create(vaccinationCard);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserDoesNotExist() {
        when(userGateway.hasUser(vaccinationCard.getEmail())).thenReturn(false);

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> sendVaccinationCardUseCase.execute(vaccinationCard)
        );

        assertEquals("Usuário não encontrado. Por favor tente novamente", exception.getMessage());

        verify(userGateway, times(1)).hasUser(vaccinationCard.getEmail());
        verify(vaccinationCardGateway, never()).findByEmail(anyString());
        verify(vaccinationCardGateway, never()).create(any());
    }

    @Test
    void shouldThrowAlreadyExistExceptionWhenCardAlreadyExists() {
        when(userGateway.hasUser(vaccinationCard.getEmail())).thenReturn(true);
        when(vaccinationCardGateway.findByEmail(vaccinationCard.getEmail()))
                .thenReturn(Optional.of(vaccinationCard));

        AlreadyExistException exception = assertThrows(
                AlreadyExistException.class,
                () -> sendVaccinationCardUseCase.execute(vaccinationCard)
        );

        assertEquals("Solicação já existe", exception.getMessage());

        verify(userGateway, times(1)).hasUser(vaccinationCard.getEmail());
        verify(vaccinationCardGateway, times(1)).findByEmail(vaccinationCard.getEmail());
        verify(vaccinationCardGateway, never()).create(any());
    }
}
