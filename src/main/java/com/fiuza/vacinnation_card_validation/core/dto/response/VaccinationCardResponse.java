package com.fiuza.vacinnation_card_validation.core.dto.response;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;

import java.util.Base64;

public record VaccinationCardResponse(
        String email,
        String frontCardBase64,
        String backCardBase64,
        String status
) {
    public static VaccinationCardResponse fromDomain(VaccinationCard card) {
        return new VaccinationCardResponse(
                card.getEmail(),
                card.getFrontCard() != null
                        ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(card.getFrontCard())
                        : null,
                card.getBackCard() != null
                        ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(card.getBackCard())
                        : null,
                card.getStatus().name()
        );
    }
}