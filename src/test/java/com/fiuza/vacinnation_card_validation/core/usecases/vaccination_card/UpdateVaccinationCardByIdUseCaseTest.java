package com.fiuza.vacinnation_card_validation.core.usecases.vaccination_card;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.enums.Status;
import com.fiuza.vacinnation_card_validation.core.exceptions.NotFoundException;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;
import com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card.UpdateVaccinationCardByIdUseCase;
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
class UpdateVaccinationCardByIdUseCaseTest {

    @Mock
    private VaccinationCardGateway vaccinationCardGateway;

    @InjectMocks
    private UpdateVaccinationCardByIdUseCase updateVaccinationCardByIdUseCase;

    private VaccinationCard existingCard;
    private UUID cardId;

    @BeforeEach
    void setUp() {
        cardId = UUID.randomUUID();

        existingCard = new VaccinationCard();
        existingCard.setId(cardId);
        existingCard.setEmail("teste@email.com");
        existingCard.setFrontCard("front".getBytes(StandardCharsets.UTF_8));
        existingCard.setBackCard("back".getBytes(StandardCharsets.UTF_8));
        existingCard.setStatus(Status.RECEIVED);
        existingCard.setMessage("Cartão recebido");
    }

    @Test
    void shouldUpdateVaccinationCardSuccessfullyWhenIdExists() {
        Status newStatus = Status.APPROVED;
        String newMessage = "Cartão aprovado com sucesso";

        when(vaccinationCardGateway.findById(cardId)).thenReturn(Optional.of(existingCard));

        VaccinationCard updatedCard = new VaccinationCard(
                existingCard.getId(),
                existingCard.getEmail(),
                existingCard.getFrontCard(),
                existingCard.getBackCard(),
                newStatus,
                newMessage
        );

        when(vaccinationCardGateway.update(any(VaccinationCard.class))).thenReturn(updatedCard);

        VaccinationCard result = updateVaccinationCardByIdUseCase.execute(newStatus, newMessage, cardId);

        assertNotNull(result);
        assertEquals(cardId, result.getId());
        assertEquals(existingCard.getEmail(), result.getEmail());
        assertArrayEquals(existingCard.getFrontCard(), result.getFrontCard());
        assertArrayEquals(existingCard.getBackCard(), result.getBackCard());
        assertEquals(newStatus, result.getStatus());
        assertEquals(newMessage, result.getMessage());

        verify(vaccinationCardGateway, times(1)).findById(cardId);
        verify(vaccinationCardGateway, times(1)).update(any(VaccinationCard.class));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenIdDoesNotExist() {
        when(vaccinationCardGateway.findById(cardId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> updateVaccinationCardByIdUseCase.execute(Status.APPROVED, "Mensagem", cardId)
        );

        assertEquals("Solicitação não encontrada. Tente novamente", exception.getMessage());

        verify(vaccinationCardGateway, times(1)).findById(cardId);
        verify(vaccinationCardGateway, never()).update(any(VaccinationCard.class));
    }
}