package com.fiuza.vacinnation_card_validation.infra.adapter;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.enums.Status;
import com.fiuza.vacinnation_card_validation.infra.model.StatusModel;
import com.fiuza.vacinnation_card_validation.infra.model.VaccinationCardModel;
import com.fiuza.vacinnation_card_validation.infra.repositories.VaccinationCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VaccinationCardRepositoryImpTest {

    @Mock
    private VaccinationCardRepository vaccinationCardRepository;

    @InjectMocks
    private VaccinationCardRepositoryImp vaccinationCardRepositoryImp;

    private UUID cardId;
    private VaccinationCard vaccinationCard;
    private VaccinationCardModel vaccinationCardModel;

    @BeforeEach
    void setUp() {
        cardId = UUID.randomUUID();

        vaccinationCard = new VaccinationCard(
                cardId,
                "teste@teste.com",
                new byte[]{1, 2, 3},
                new byte[]{4, 5, 6},
                Status.RECEIVED,
                "Aguardando validação"
        );

        vaccinationCardModel = new VaccinationCardModel(
                cardId,
                "teste@teste.com",
                new byte[]{1, 2, 3},
                new byte[]{4, 5, 6},
                StatusModel.RECEIVED,
                "Aguardando validação"
        );
    }

    @Test
    void shouldCreateVaccinationCardSuccessfully() {
        when(vaccinationCardRepository.save(any(VaccinationCardModel.class)))
                .thenReturn(vaccinationCardModel);

        VaccinationCard result = vaccinationCardRepositoryImp.create(vaccinationCard);

        assertNotNull(result);
        assertEquals(vaccinationCard.getId(), result.getId());
        assertEquals(vaccinationCard.getEmail(), result.getEmail());
        assertArrayEquals(vaccinationCard.getFrontCard(), result.getFrontCard());
        assertArrayEquals(vaccinationCard.getBackCard(), result.getBackCard());
        assertEquals(vaccinationCard.getStatus(), result.getStatus());

        verify(vaccinationCardRepository, times(1)).save(any(VaccinationCardModel.class));
    }

    @Test
    void shouldFindByEmailWhenExists() {
        when(vaccinationCardRepository.findByEmail("teste@teste.com"))
                .thenReturn(Optional.of(vaccinationCardModel));

        Optional<VaccinationCard> result = vaccinationCardRepositoryImp.findByEmail("teste@teste.com");

        assertTrue(result.isPresent());
        assertEquals("teste@teste.com", result.get().getEmail());
        assertEquals(cardId, result.get().getId());
        verify(vaccinationCardRepository, times(1)).findByEmail("teste@teste.com");
    }

    @Test
    void shouldReturnEmptyWhenFindByEmailDoesNotExist() {
        when(vaccinationCardRepository.findByEmail("naoexiste@teste.com"))
                .thenReturn(Optional.empty());

        Optional<VaccinationCard> result = vaccinationCardRepositoryImp.findByEmail("naoexiste@teste.com");

        assertFalse(result.isPresent());
        verify(vaccinationCardRepository, times(1)).findByEmail("naoexiste@teste.com");
    }

    @Test
    void shouldFindByIdWhenExists() {
        when(vaccinationCardRepository.findById(cardId))
                .thenReturn(Optional.of(vaccinationCardModel));

        Optional<VaccinationCard> result = vaccinationCardRepositoryImp.findById(cardId);

        assertTrue(result.isPresent());
        assertEquals(cardId, result.get().getId());
        verify(vaccinationCardRepository, times(1)).findById(cardId);
    }

    @Test
    void shouldReturnEmptyWhenFindByIdDoesNotExist() {
        when(vaccinationCardRepository.findById(cardId))
                .thenReturn(Optional.empty());

        Optional<VaccinationCard> result = vaccinationCardRepositoryImp.findById(cardId);

        assertFalse(result.isPresent());
        verify(vaccinationCardRepository, times(1)).findById(cardId);
    }

    @Test
    void shouldUpdateVaccinationCardSuccessfully() {
        when(vaccinationCardRepository.save(any(VaccinationCardModel.class)))
                .thenReturn(vaccinationCardModel);

        VaccinationCard result = vaccinationCardRepositoryImp.update(vaccinationCard);

        assertNotNull(result);
        assertEquals(vaccinationCard.getId(), result.getId());
        assertEquals(vaccinationCard.getEmail(), result.getEmail());
        assertArrayEquals(vaccinationCard.getFrontCard(), result.getFrontCard());
        assertArrayEquals(vaccinationCard.getBackCard(), result.getBackCard());
        assertEquals(vaccinationCard.getStatus(), result.getStatus());

        verify(vaccinationCardRepository, times(1)).save(any(VaccinationCardModel.class));
    }
}
