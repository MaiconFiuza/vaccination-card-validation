package com.fiuza.vacinnation_card_validation.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiuza.vacinnation_card_validation.application.controllers.VaccinationController;
import com.fiuza.vacinnation_card_validation.core.dto.request.VaccinationRequest;
import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;
import com.fiuza.vacinnation_card_validation.core.enums.Type;
import com.fiuza.vacinnation_card_validation.core.usecases.vaccination.CreateVaccinationUseCase;
import com.fiuza.vacinnation_card_validation.infra.model.TypeModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VaccinationController.class)
class VaccinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateVaccinationUseCase createVaccinationUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateVaccinationSuccessfully() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID vaccinationId = UUID.randomUUID();

        VaccinationRequest request = new VaccinationRequest(
                "COVID-19",
                LocalDate.of(2025, 1, 10),
                Type.PRIMEIRA_DOSE
        );

        Vaccination vaccination = new Vaccination(
                vaccinationId,
                request.name(),
                request.date(),
                Type.PRIMEIRA_DOSE,
                new User(userId)
        );

        Mockito.when(createVaccinationUseCase.execute(any(Vaccination.class), eq(userId)))
                .thenReturn(vaccination);

        // When & Then
        mockMvc.perform(post("/vaccination/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(vaccinationId.toString()))
                .andExpect(jsonPath("$.name").value("COVID-19"))
                .andExpect(jsonPath("$.date").value("2025-01-10"))
                .andExpect(jsonPath("$.type").value("PRIMEIRA_DOSE"));
    }
}
