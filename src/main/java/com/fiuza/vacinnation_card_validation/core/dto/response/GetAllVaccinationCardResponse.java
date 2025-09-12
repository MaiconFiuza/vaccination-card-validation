package com.fiuza.vacinnation_card_validation.core.dto.response;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;

import java.util.UUID;

public record GetAllVaccinationCardResponse(
        UUID id,
        String email,
        String status
) {
    public static GetAllVaccinationCardResponse fromDomain(VaccinationCard card) {
        return new GetAllVaccinationCardResponse(
                card.getId(),
                card.getEmail(),
                card.getStatus().name()
        );
    }
}
