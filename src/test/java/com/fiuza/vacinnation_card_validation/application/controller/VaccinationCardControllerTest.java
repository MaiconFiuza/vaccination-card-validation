package com.fiuza.vacinnation_card_validation.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiuza.vacinnation_card_validation.application.controllers.VaccinationCardController;
import com.fiuza.vacinnation_card_validation.core.dto.request.UpdateVaccinationCardRequest;
import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.enums.Status;
import com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VaccinationCardController.class)
class VaccinationCardControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SendVaccinationCardUseCase sendVaccinationCardUseCase;

    @MockBean
    private GetVaccinationCardByIdUseCase getVaccinationCardByIdUseCase;

    @MockBean
    private GetVaccinationCardByEmailUseCase getVaccinationCardByEmailUseCase;

    @MockBean
    private UpdateVaccinationCardByIdUseCase updateVaccinationCardByIdUseCase;

    @MockBean
    private GetAllVaccinationCardByEmailUseCase getAllVaccinationCardByEmailUseCase;

    @MockBean
    private DeleteVaccinationCardByIdUseCase deleteVaccinationCardByIdUseCase;

    @Test
    void shouldCreateVaccinationCard() throws Exception {
        UUID id = UUID.randomUUID();
        MockMultipartFile front = new MockMultipartFile("frontCard", "front.png",
                "image/png", "front".getBytes());
        MockMultipartFile back = new MockMultipartFile("backCard", "back.png",
                "image/png", "back".getBytes());

        VaccinationCard card = new VaccinationCard(id, "test@email.com", front.getBytes(),
                back.getBytes(), Status.RECEIVED, null);

        Mockito.when(sendVaccinationCardUseCase.execute(any(VaccinationCard.class)))
                .thenReturn(card);

        mockMvc.perform(multipart("/vaccination-card")
                        .file(front)
                        .file(back)
                        .header("x-user-email", "test@email.com"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/vaccination-card/" + id));
    }

    @Test
    void shouldGetVaccinationCardById() throws Exception {
        UUID id = UUID.randomUUID();
        VaccinationCard card = new VaccinationCard(id, "test@email.com",
                new byte[0], new byte[0], Status.RECEIVED, null);

        Mockito.when(getVaccinationCardByIdUseCase.execute(id)).thenReturn(Optional.of(card));

        mockMvc.perform(get("/vaccination-card/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.status").value("RECEIVED"));
    }

    @Test
    void shouldReturnNotFoundIfVaccinationCardByIdNotExist() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(getVaccinationCardByIdUseCase.execute(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/vaccination-card/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllVaccinationCards() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        VaccinationCard card1 = new VaccinationCard(id1, "a@email.com",
                new byte[0], new byte[0], Status.RECEIVED, null);
        VaccinationCard card2 = new VaccinationCard(id2, "b@email.com",
                new byte[0], new byte[0], Status.APPROVED, null);

        Mockito.when(getAllVaccinationCardByEmailUseCase.execute())
                .thenReturn(List.of(card1, card2));

        mockMvc.perform(get("/vaccination-card")
                        .header("x-user-email", "any@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1.toString()))
                .andExpect(jsonPath("$[0].email").value("a@email.com"))
                .andExpect(jsonPath("$[0].status").value("RECEIVED"))
                .andExpect(jsonPath("$[1].id").value(id2.toString()))
                .andExpect(jsonPath("$[1].email").value("b@email.com"))
                .andExpect(jsonPath("$[1].status").value("APPROVED"));
    }

    @Test
    void shouldUpdateVaccinationCard() throws Exception {
        UUID id = UUID.randomUUID();
        UpdateVaccinationCardRequest request = new UpdateVaccinationCardRequest(Status.APPROVED, "Updated");

        mockMvc.perform(put("/vaccination-card/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        Mockito.verify(updateVaccinationCardByIdUseCase)
                .execute(eq(Status.APPROVED), eq("Updated"), eq(id));
    }

    @Test
    void shouldDeleteVaccinationCard() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/vaccination-card/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(deleteVaccinationCardByIdUseCase).execute(eq(id));
    }

    @Test
    void shouldGetVaccinationCardByEmail() throws Exception {
        UUID id = UUID.randomUUID();
        VaccinationCard card = new VaccinationCard(id, "test@email.com", new byte[0],
                new byte[0], Status.RECEIVED, null);

        Mockito.when(getVaccinationCardByEmailUseCase.execute("test@email.com"))
                .thenReturn(Optional.of(card));

        mockMvc.perform(get("/vaccination-card/email/{email}", "test@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.status").value("RECEIVED"));
    }

    @Test
    void shouldReturnNotFoundIfVaccinationCardByEmailNotExist() throws Exception {
        Mockito.when(getVaccinationCardByEmailUseCase.execute("notfound@email.com"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/vaccination-card/email/{email}", "notfound@email.com"))
                .andExpect(status().isNotFound());
    }
}