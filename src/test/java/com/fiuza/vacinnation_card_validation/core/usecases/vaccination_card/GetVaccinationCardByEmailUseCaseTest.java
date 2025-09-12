package com.fiuza.vacinnation_card_validation.core.usecases.vaccination_card;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.enums.Status;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;
import com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card.GetVaccinationCardByEmailUseCase;
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
class GetVaccinationCardByEmailUseCaseTest {

    @Mock
    private VaccinationCardGateway vaccinationCardGateway;

    @InjectMocks
    private GetVaccinationCardByEmailUseCase getVaccinationCardByEmailUseCase;

    private VaccinationCard vaccinationCard;
    private String email;

    @BeforeEach
    void setUp() {
        email = "teste@email.com";
        vaccinationCard = new VaccinationCard();
        vaccinationCard.setId(UUID.randomUUID());
        vaccinationCard.setEmail(email);
        vaccinationCard.setFrontCard("front".getBytes(StandardCharsets.UTF_8));
        vaccinationCard.setBackCard("back".getBytes(StandardCharsets.UTF_8));
        vaccinationCard.setStatus(Status.RECEIVED);
        vaccinationCard.setMessage("Cartão recebido com sucesso");
    }

    @Test
    void shouldReturnVaccinationCardWhenEmailExists() {
        // Given
        when(vaccinationCardGateway.findByEmail(email)).thenReturn(Optional.of(vaccinationCard));

        // When
        Optional<VaccinationCard> result = getVaccinationCardByEmailUseCase.execute(email);

        // Then
        assertTrue(result.isPresent());
        assertEquals(vaccinationCard.getId(), result.get().getId());
        assertEquals(vaccinationCard.getEmail(), result.get().getEmail());
        assertArrayEquals(vaccinationCard.getFrontCard(), result.get().getFrontCard());
        assertArrayEquals(vaccinationCard.getBackCard(), result.get().getBackCard());
        assertEquals(vaccinationCard.getStatus(), result.get().getStatus());
        assertEquals(vaccinationCard.getMessage(), result.get().getMessage());

        // Verifica se o gateway foi chamado corretamente
        verify(vaccinationCardGateway, times(1)).findByEmail(email);
    }

    @Test
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        // Given
        when(vaccinationCardGateway.findByEmail(email)).thenReturn(Optional.empty());

        // When
        Optional<VaccinationCard> result = getVaccinationCardByEmailUseCase.execute(email);

        // Then
        assertFalse(result.isPresent());

        // Verifica se o gateway foi chamado corretamente
        verify(vaccinationCardGateway, times(1)).findByEmail(email);
    }
}
