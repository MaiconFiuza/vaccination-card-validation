package com.fiuza.vacinnation_card_validation.application.controllers;

import com.fiuza.vacinnation_card_validation.core.dto.response.UserResponse;
import com.fiuza.vacinnation_card_validation.core.usecases.user.GetUserByEmailUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private static  final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final GetUserByEmailUseCase getUserByEmailUseCase;

    public UserController(
            GetUserByEmailUseCase getUserByEmailUseCase
    ) {
        this.getUserByEmailUseCase = getUserByEmailUseCase;
    }

    @Operation(
            description = "Busca um usuário por email",
            summary = "Endpoint responsável por buscar um usuário",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getByEmail(
            @PathVariable String email
    ) {
        logger.info("GET /{}", email);
        return getUserByEmailUseCase.execute(email)
                .map(user -> ResponseEntity.ok(UserResponse.fromDomain(user)))
                .orElse(ResponseEntity.notFound().build());
    }

}
