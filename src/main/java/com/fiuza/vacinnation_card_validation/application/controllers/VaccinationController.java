package com.fiuza.vacinnation_card_validation.application.controllers;

import com.fiuza.vacinnation_card_validation.core.dto.request.VaccinationRequest;
import com.fiuza.vacinnation_card_validation.core.dto.response.VaccinationResponse;
import com.fiuza.vacinnation_card_validation.core.usecases.vaccination.CreateVaccinationUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/vaccination")
public class VaccinationController {
    private static  final Logger logger = LoggerFactory.getLogger(VaccinationController.class);

    private final CreateVaccinationUseCase createVaccinationUseCase;

    public VaccinationController(CreateVaccinationUseCase createVaccinationUseCase) {
        this.createVaccinationUseCase = createVaccinationUseCase;
    }

    @PostMapping("{id}")
    public ResponseEntity<VaccinationResponse> create(
            @RequestBody VaccinationRequest vaccinationRequest,
            @PathVariable UUID id
    ) {
        var result = createVaccinationUseCase.execute(VaccinationRequest.fromDomain(vaccinationRequest), id);

        return ResponseEntity.status(HttpStatus.CREATED).body(VaccinationResponse.fromDomain(result));
    }

}
