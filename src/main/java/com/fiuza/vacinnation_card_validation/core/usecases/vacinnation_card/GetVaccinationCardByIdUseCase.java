package com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;

import java.util.Optional;
import java.util.UUID;

public class GetVaccinationCardByIdUseCase {
    private VaccinationCardGateway vaccinationCardGateway;

    public GetVaccinationCardByIdUseCase(VaccinationCardGateway vaccinationCardGateway) {
        this.vaccinationCardGateway = vaccinationCardGateway;
    }

    public Optional<VaccinationCard> execute(UUID id) {
        return vaccinationCardGateway.findById(id);
    }

}
