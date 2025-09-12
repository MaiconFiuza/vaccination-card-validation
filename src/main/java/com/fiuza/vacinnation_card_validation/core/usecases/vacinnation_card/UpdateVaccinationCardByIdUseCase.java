package com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.enums.Status;
import com.fiuza.vacinnation_card_validation.core.exceptions.NotFoundException;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;

import java.util.Optional;
import java.util.UUID;

public class UpdateVaccinationCardByIdUseCase {
    private final VaccinationCardGateway vaccinationCardGateway;

    public UpdateVaccinationCardByIdUseCase(VaccinationCardGateway vaccinationCardGateway) {
        this.vaccinationCardGateway = vaccinationCardGateway;
    }

    public VaccinationCard execute(Status status, String message, UUID id) {
        Optional<VaccinationCard> vaccinationCard = vaccinationCardGateway.findById(id);
        if(vaccinationCard.isEmpty()) throw new NotFoundException("Solicitação não encontrada. Tente novamente");

        VaccinationCard updatedVaccinationCard = new VaccinationCard(
                vaccinationCard.get().getId(), vaccinationCard.get().getEmail(), vaccinationCard.get().getFrontCard(),
                vaccinationCard.get().getBackCard(), status, message);

        return vaccinationCardGateway.update(updatedVaccinationCard);
    }

}
