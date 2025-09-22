package com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.enums.Status;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;

import java.util.List;

public class GetAllVaccinationCardByEmailUseCase {
    private VaccinationCardGateway vaccinationCardGateway;

    public GetAllVaccinationCardByEmailUseCase(VaccinationCardGateway vaccinationCardGateway) {
        this.vaccinationCardGateway = vaccinationCardGateway;
    }

    public List<VaccinationCard> execute() {return vaccinationCardGateway.findAll()
            .stream()
            .filter(card -> card.getStatus() == Status.RECEIVED)
            .toList();
    }
}
