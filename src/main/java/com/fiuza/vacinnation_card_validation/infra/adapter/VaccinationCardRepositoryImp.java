package com.fiuza.vacinnation_card_validation.infra.adapter;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;
import com.fiuza.vacinnation_card_validation.infra.mappers.VacinationCardMapper;
import com.fiuza.vacinnation_card_validation.infra.model.VaccinationCardModel;
import com.fiuza.vacinnation_card_validation.infra.repositories.VaccinationCardRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VaccinationCardRepositoryImp implements VaccinationCardGateway {
    private final VaccinationCardRepository vaccinationCardRepository;

    public VaccinationCardRepositoryImp(VaccinationCardRepository vaccinationCardRepository) {
        this.vaccinationCardRepository = vaccinationCardRepository;
    }


    @Override
    public VaccinationCard create(VaccinationCard vaccinationCard) {
        VaccinationCardModel vaccinationCardModel = VacinationCardMapper
                .vacinationCardToVacinationCardModel(vaccinationCard);
        VaccinationCardModel vacinationCardSaved = vaccinationCardRepository.save(vaccinationCardModel);

        return VacinationCardMapper.vacinationCardModelToVacinationCard(vacinationCardSaved);
    }

    @Override
    public Optional<VaccinationCard> findByEmail(String email) {
        return vaccinationCardRepository.findByEmail(email)
                .map(VacinationCardMapper::vacinationCardModelToVacinationCard);
    }

    @Override
    public Optional<VaccinationCard> findById(UUID id) {
        return vaccinationCardRepository.findById(id)
                .map(VacinationCardMapper::vacinationCardModelToVacinationCard);
    }

    @Override
    public List<VaccinationCard> findAll() {
        return vaccinationCardRepository.findAll().stream()
                .map(VacinationCardMapper::vacinationCardModelToVacinationCard).toList();
    }

    @Override
    public VaccinationCard update(VaccinationCard vaccinationCard) {
        VaccinationCardModel vaccinationCardModel = VacinationCardMapper
                .vacinationCardToVacinationCardModel(vaccinationCard);
        VaccinationCardModel vacinationCardSaved = vaccinationCardRepository.save(vaccinationCardModel);

        return VacinationCardMapper.vacinationCardModelToVacinationCard(vacinationCardSaved);
    }

    @Override
    public void delete(UUID id) {
        vaccinationCardRepository.deleteById(id);
    }
}
