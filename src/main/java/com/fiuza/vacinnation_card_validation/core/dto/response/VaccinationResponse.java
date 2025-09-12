package com.fiuza.vacinnation_card_validation.core.dto.response;

import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;
import com.fiuza.vacinnation_card_validation.core.enums.Type;

import java.time.LocalDate;
import java.util.UUID;

public record VaccinationResponse(
        UUID id,
        String name,
        LocalDate date,
        Type type
) {
    public static VaccinationResponse fromDomain(Vaccination vaccination) {
        return new VaccinationResponse(
                vaccination.getId(),
                vaccination.getName(),
                vaccination.getDate(),
                vaccination.getType()
        );
    }

}
