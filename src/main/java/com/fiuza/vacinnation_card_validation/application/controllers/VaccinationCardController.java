package com.fiuza.vacinnation_card_validation.application.controllers;

import com.fiuza.vacinnation_card_validation.core.dto.request.UpdateVaccinationCardRequest;
import com.fiuza.vacinnation_card_validation.core.dto.request.VaccinationCardRequest;
import com.fiuza.vacinnation_card_validation.core.dto.response.GetAllVaccinationCardResponse;
import com.fiuza.vacinnation_card_validation.core.dto.response.VaccinationCardResponse;
import com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card.*;
import com.fiuza.vacinnation_card_validation.infra.mappers.VacinationCardMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vaccination-card")
public class VaccinationCardController {
    private static  final Logger logger = LoggerFactory.getLogger(VaccinationCardController.class);

    private final SendVaccinationCardUseCase sendVaccinationCardUseCase;
    private final GetVaccinationCardByIdUseCase getVaccinationCardByIdUseCase;
    private final GetVaccinationCardByEmailUseCase getVaccinationCardByEmailUseCase;
    private final UpdateVaccinationCardByIdUseCase updateVaccinationCardByIdUseCase;
    private final GetAllVaccinationCardByEmailUseCase getAllVaccinationCardByEmailUseCase;
    private final DeleteVaccinationCardByIdUseCase deleteVaccinationCardByIdUseCase;

    public VaccinationCardController(
       SendVaccinationCardUseCase sendVaccinationCardUseCase,
       GetVaccinationCardByIdUseCase getVaccinationCardByIdUseCase,
       GetVaccinationCardByEmailUseCase getVaccinationCardByEmailUseCase,
       UpdateVaccinationCardByIdUseCase updateVaccinationCardByIdUseCase,
       GetAllVaccinationCardByEmailUseCase getAllVaccinationCardByEmailUseCase,
       DeleteVaccinationCardByIdUseCase deleteVaccinationCardByIdUseCase
    ) {
        this.sendVaccinationCardUseCase = sendVaccinationCardUseCase;
        this.getVaccinationCardByIdUseCase = getVaccinationCardByIdUseCase;
        this.getVaccinationCardByEmailUseCase = getVaccinationCardByEmailUseCase;
        this.getAllVaccinationCardByEmailUseCase = getAllVaccinationCardByEmailUseCase;
        this.updateVaccinationCardByIdUseCase = updateVaccinationCardByIdUseCase;
        this.deleteVaccinationCardByIdUseCase = deleteVaccinationCardByIdUseCase;
    }

    @Operation(
            description = "Envio do cartão de vacina",
            summary = "Endpoint responsável pelo envio do cartão de vacina",
            responses = {
                    @ApiResponse(description = "CREATED", responseCode = "204")
            }
    )
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestHeader("x-user-email") String email,
            @RequestParam("frontCard") MultipartFile frontCard,
            @RequestParam("backCard") MultipartFile backCard
    ) {
        logger.info("POST");
        VaccinationCardRequest card = new VaccinationCardRequest(email, frontCard, backCard);
        var result = sendVaccinationCardUseCase.execute(VacinationCardMapper.toDomain(card));
        URI location = URI.create("/vaccination-card/" + result.getId());

        return ResponseEntity.created(location).build();
    }

    @Operation(
            description = "Buscar uma solicitação pelo id",
            summary = "Endpoint responsável por buscar uma solicitação pelo id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VaccinationCardResponse> getById(
            @PathVariable UUID id
    ) {
        logger.info("GET /{}", id);
        return getVaccinationCardByIdUseCase.execute(id)
                .map(card -> ResponseEntity.ok(VaccinationCardResponse.fromDomain(card)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            description = "Buscar todas as solicitações",
            summary = "Endpoint responsável por buscar todas as solicitação",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @GetMapping
    public ResponseEntity<List<GetAllVaccinationCardResponse>> getAll(@RequestHeader("x-user-email") String email) {
        logger.info("GET");
        List<GetAllVaccinationCardResponse> responses = getAllVaccinationCardByEmailUseCase.execute().stream()
                .map(GetAllVaccinationCardResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @Operation(
            description = "Buscar a solicitação pelo email do usuário",
            summary = "Endpoint responsável por atualizar uma solicitação",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @GetMapping("/email/{email}")
    public ResponseEntity<VaccinationCardResponse> getByEmail(
            @PathVariable String email
    ) {
        logger.info("GET /email/{}", email);
        return getVaccinationCardByEmailUseCase.execute(email)
                .map(card -> ResponseEntity.ok(VaccinationCardResponse.fromDomain(card)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            description = "Atualiza uma solicitação aprovando ou reprovando",
            summary = "Endpoint responsável por atualizar uma solicitação",
            responses = {
                    @ApiResponse(description = "noContent", responseCode = "204")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable UUID id,
            @RequestBody UpdateVaccinationCardRequest request
    ) {
        logger.info("PUT /{}", id);
        updateVaccinationCardByIdUseCase.execute(request.status(), request.message(), id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Excluir uma solicitação já realizada",
            summary = "Endpoint responsável por excluir uma solicitação já realizada",
            responses = {
                    @ApiResponse(description = "noContent", responseCode = "204")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        logger.info("DELETE /{}", id);
        deleteVaccinationCardByIdUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}
