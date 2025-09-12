package com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;

import java.util.Optional;

public class GetVaccinationCardByEmailUseCase {
    private VaccinationCardGateway vaccinationCardGateway;

    public GetVaccinationCardByEmailUseCase(VaccinationCardGateway vaccinationCardGateway) {
        this.vaccinationCardGateway = vaccinationCardGateway;
    }

    public Optional<VaccinationCard> execute(String email) {
        return vaccinationCardGateway.findByEmail(email);
    }

}
