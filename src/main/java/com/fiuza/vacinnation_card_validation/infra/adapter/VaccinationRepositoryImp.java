package com.fiuza.vacinnation_card_validation.infra.adapter;

import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationGateway;
import com.fiuza.vacinnation_card_validation.infra.mappers.VaccinationMapper;
import com.fiuza.vacinnation_card_validation.infra.model.VaccinationModel;
import com.fiuza.vacinnation_card_validation.infra.repositories.VaccinationRepository;

import java.util.Optional;
import java.util.UUID;

public class VaccinationRepositoryImp implements VaccinationGateway {
    private final VaccinationRepository vaccinationRepository;

    public VaccinationRepositoryImp(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    @Override
    public Optional<Vaccination> findById(UUID id) {
        return vaccinationRepository.findById(id)
                .map(VaccinationMapper::vacinationModelToVacination);
    }

    @Override
    public Vaccination create(Vaccination vaccination) {
        VaccinationModel vaccinationModel = VaccinationMapper.vacinationToVacinationModel(vaccination);
        VaccinationModel vaccinationSaved = vaccinationRepository.save(vaccinationModel);

        return VaccinationMapper.vacinationModelToVacination(vaccinationSaved);
    }
}
