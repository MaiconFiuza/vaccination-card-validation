package com.fiuza.vacinnation_card_validation.core.dto.request;

import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;
import com.fiuza.vacinnation_card_validation.core.enums.Type;

import java.time.LocalDate;

public record VaccinationRequest(
     String name,
     LocalDate date,
     Type type
) {
    public static Vaccination fromDomain(VaccinationRequest vaccinationRequest) {
        return new Vaccination(
            null,
            vaccinationRequest.name,
            vaccinationRequest.date,
            vaccinationRequest.type,
            null
        );
    }

}
