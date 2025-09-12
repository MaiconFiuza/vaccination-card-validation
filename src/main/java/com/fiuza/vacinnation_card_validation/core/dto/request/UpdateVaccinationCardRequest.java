package com.fiuza.vacinnation_card_validation.core.dto.request;

import com.fiuza.vacinnation_card_validation.core.enums.Status;

public record UpdateVaccinationCardRequest(
        Status status,
        String message
) {
}
