package com.fiuza.vacinnation_card_validation.core.usecases.vaccination_card;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.enums.Status;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;
import com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card.GetVaccinationCardByIdUseCase;
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
class GetVaccinationCardByIdUseCaseTest {

    @Mock
    private VaccinationCardGateway vaccinationCardGateway;

    @InjectMocks
    private GetVaccinationCardByIdUseCase getVaccinationCardByIdUseCase;

    private VaccinationCard vaccinationCard;
    private UUID vaccinationCardId;

    @BeforeEach
    void setUp() {
        vaccinationCardId = UUID.randomUUID();

        vaccinationCard = new VaccinationCard();
        vaccinationCard.setId(vaccinationCardId);
        vaccinationCard.setEmail("teste@email.com");
        vaccinationCard.setFrontCard("front".getBytes(StandardCharsets.UTF_8));
        vaccinationCard.setBackCard("back".getBytes(StandardCharsets.UTF_8));
        vaccinationCard.setStatus(Status.RECEIVED);
        vaccinationCard.setMessage("Cartão encontrado com sucesso");
    }

    @Test
    void shouldReturnVaccinationCardWhenIdExists() {
        // Given
        when(vaccinationCardGateway.findById(vaccinationCardId))
                .thenReturn(Optional.of(vaccinationCard));

        // When
        Optional<VaccinationCard> result = getVaccinationCardByIdUseCase.execute(vaccinationCardId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(vaccinationCard.getId(), result.get().getId());
        assertEquals(vaccinationCard.getEmail(), result.get().getEmail());
        assertArrayEquals(vaccinationCard.getFrontCard(), result.get().getFrontCard());
        assertArrayEquals(vaccinationCard.getBackCard(), result.get().getBackCard());
        assertEquals(vaccinationCard.getStatus(), result.get().getStatus());
        assertEquals(vaccinationCard.getMessage(), result.get().getMessage());

        verify(vaccinationCardGateway, times(1)).findById(vaccinationCardId);
    }

    @Test
    void shouldReturnEmptyWhenIdDoesNotExist() {
        when(vaccinationCardGateway.findById(vaccinationCardId))
                .thenReturn(Optional.empty());

        Optional<VaccinationCard> result = getVaccinationCardByIdUseCase.execute(vaccinationCardId);

        assertFalse(result.isPresent());

        verify(vaccinationCardGateway, times(1)).findById(vaccinationCardId);
    }
}
