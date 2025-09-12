package com.fiuza.vacinnation_card_validation.infra.repositories;

import com.fiuza.vacinnation_card_validation.infra.model.VaccinationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VaccinationRepository extends JpaRepository<VaccinationModel, UUID> {
}
