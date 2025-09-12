package com.fiuza.vacinnation_card_validation.core.gateway;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VaccinationCardGateway {

    VaccinationCard create(VaccinationCard vacination);

    Optional<VaccinationCard> findByEmail(String email);

    Optional<VaccinationCard> findById(UUID id);

    List<VaccinationCard> findAll();

    VaccinationCard update(VaccinationCard vaccinationCard);

    void delete(UUID id);

}
