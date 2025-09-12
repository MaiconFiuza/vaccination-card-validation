package com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card;

import com.fiuza.vacinnation_card_validation.core.exceptions.NotFoundException;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;

import java.util.UUID;

public class DeleteVaccinationCardByIdUseCase {
    private VaccinationCardGateway vaccinationCardGateway;

    public DeleteVaccinationCardByIdUseCase(VaccinationCardGateway vaccinationCardGateway) {
        this.vaccinationCardGateway = vaccinationCardGateway;
    }

    public void execute(UUID id) {
        vaccinationCardGateway.findById(id)
                .orElseThrow(() -> new NotFoundException("Cartão não encontrado. Por favor tente novamente"));
        vaccinationCardGateway.delete(id);
    }
}
