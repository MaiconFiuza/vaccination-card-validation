package com.fiuza.vacinnation_card_validation.infra.mappers;

import com.fiuza.vacinnation_card_validation.core.dto.request.VaccinationCardRequest;
import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.enums.Status;
import com.fiuza.vacinnation_card_validation.core.exceptions.InvalidFileException;
import com.fiuza.vacinnation_card_validation.infra.model.StatusModel;
import com.fiuza.vacinnation_card_validation.infra.model.VaccinationCardModel;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.mock.web.MockMultipartFile;

class VaccinationCardMapperTest {

    @Test
    void shouldMapVaccinationCardToVaccinationCardModel() {
        UUID id = UUID.randomUUID();
        byte[] frontCard = "front".getBytes(StandardCharsets.UTF_8);
        byte[] backCard = "back".getBytes(StandardCharsets.UTF_8);

        VaccinationCard vaccinationCard = new VaccinationCard(
                id,
                "teste@email.com",
                frontCard,
                backCard,
                Status.RECEIVED
        );

        VaccinationCardModel vaccinationCardModel =
                VacinationCardMapper.vacinationCardToVacinationCardModel(vaccinationCard);

        assertNotNull(vaccinationCardModel);
        assertEquals(vaccinationCard.getId(), vaccinationCardModel.getId());
        assertEquals(vaccinationCard.getEmail(), vaccinationCardModel.getEmail());
        assertArrayEquals(vaccinationCard.getFrontCard(), vaccinationCardModel.getFrontCard());
        assertArrayEquals(vaccinationCard.getBackCard(), vaccinationCardModel.getBackCard());
        assertEquals(vaccinationCard.getStatus().name(), vaccinationCardModel.getStatusModel().name());
    }

    @Test
    void shouldMapVaccinationCardModelToVaccinationCard() {
        UUID id = UUID.randomUUID();
        byte[] frontCard = "front".getBytes(StandardCharsets.UTF_8);
        byte[] backCard = "back".getBytes(StandardCharsets.UTF_8);

        VaccinationCardModel vaccinationCardModel = new VaccinationCardModel(
                id,
                "teste@email.com",
                frontCard,
                backCard,
                StatusModel.RECEIVED,
                ""
        );

        VaccinationCard vaccinationCard =
                VacinationCardMapper.vacinationCardModelToVacinationCard(vaccinationCardModel);

        assertNotNull(vaccinationCard);
        assertEquals(vaccinationCardModel.getId(), vaccinationCard.getId());
        assertEquals(vaccinationCardModel.getEmail(), vaccinationCard.getEmail());
        assertArrayEquals(vaccinationCardModel.getFrontCard(), vaccinationCard.getFrontCard());
        assertArrayEquals(vaccinationCardModel.getBackCard(), vaccinationCard.getBackCard());
        assertEquals(vaccinationCardModel.getStatusModel().name(), vaccinationCard.getStatus().name());
    }

    @Test
    void shouldMapVaccinationCardRequestToDomain() throws Exception {
        MockMultipartFile frontCardFile = new MockMultipartFile(
                "front-card", "front content".getBytes(StandardCharsets.UTF_8)
        );
        MockMultipartFile backCardFile = new MockMultipartFile(
                "back-card", "back content".getBytes(StandardCharsets.UTF_8)
        );

        VaccinationCardRequest request = new VaccinationCardRequest(
                "teste@email.com",
                frontCardFile,
                backCardFile
        );

        VaccinationCard vaccinationCard = VacinationCardMapper.toDomain(request);

        assertNotNull(vaccinationCard);
        assertEquals(request.email(), vaccinationCard.getEmail());
        assertArrayEquals(request.frontCard().getBytes(), vaccinationCard.getFrontCard());
        assertArrayEquals(request.backCard().getBytes(), vaccinationCard.getBackCard());
        assertEquals(Status.RECEIVED, vaccinationCard.getStatus());
    }

    @Test
    void shouldThrowInvalidFileExceptionWhenIOExceptionOccurs() throws Exception {
        MockMultipartFile frontCardFile = new MockMultipartFile(
                "front-card", "front content".getBytes(StandardCharsets.UTF_8)
        );
        MockMultipartFile backCardFile = new MockMultipartFile(
                "back-card", "back content".getBytes(StandardCharsets.UTF_8)
        );

        MultipartFile corruptedFile = org.mockito.Mockito.spy(frontCardFile);
        org.mockito.Mockito.doThrow(new java.io.IOException("Erro simulado"))
                .when(corruptedFile).getBytes();

        VaccinationCardRequest request = new VaccinationCardRequest(
                "teste@email.com",
                corruptedFile,
                backCardFile
        );

        InvalidFileException exception = assertThrows(
                InvalidFileException.class,
                () -> VacinationCardMapper.toDomain(request)
        );

        assertEquals("Erro ao processar arquivos do cartão de vacinação", exception.getMessage());
    }
}
