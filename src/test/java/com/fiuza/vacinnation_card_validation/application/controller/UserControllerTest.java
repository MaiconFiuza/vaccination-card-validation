package com.fiuza.vacinnation_card_validation.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiuza.vacinnation_card_validation.application.controllers.UserController;
import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.core.usecases.user.GetUserByEmailUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetUserByEmailUseCase getUserByEmailUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnUserWhenEmailExists() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User(
                userId,
                "12345678900",
                "João Silva",
                "123456789",
                "joao.silva@email.com",
                "11999999999",
                LocalDate.of(1990, 1, 1),
                Collections.emptyList()
        );

        Mockito.when(getUserByEmailUseCase.execute("joao.silva@email.com"))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/{email}", "joao.silva@email.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@email.com"));
    }

    @Test
    void shouldReturnNotFoundWhenEmailDoesNotExist() throws Exception {
        Mockito.when(getUserByEmailUseCase.execute(anyString()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/user/{email}", "naoexiste@email.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
