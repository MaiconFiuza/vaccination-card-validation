package com.fiuza.vacinnation_card_validation.core.gateway;

import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;

import java.util.Optional;
import java.util.UUID;

public interface VaccinationGateway {

    Optional<Vaccination> findById(UUID id);

    Vaccination create(Vaccination vaccination);
}
