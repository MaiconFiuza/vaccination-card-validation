package com.fiuza.vacinnation_card_validation.infra.repositories;

import com.fiuza.vacinnation_card_validation.infra.model.VaccinationCardModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VaccinationCardRepository extends JpaRepository<VaccinationCardModel, UUID> {
    Optional<VaccinationCardModel> findByEmail(String email);
}
