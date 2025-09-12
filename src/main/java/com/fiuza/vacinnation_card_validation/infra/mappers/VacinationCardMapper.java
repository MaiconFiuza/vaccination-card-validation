package com.fiuza.vacinnation_card_validation.infra.mappers;

import com.fiuza.vacinnation_card_validation.core.dto.request.VaccinationCardRequest;
import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.enums.Status;
import com.fiuza.vacinnation_card_validation.core.exceptions.InvalidFileException;
import com.fiuza.vacinnation_card_validation.infra.model.StatusModel;
import com.fiuza.vacinnation_card_validation.infra.model.VaccinationCardModel;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VacinationCardMapper {

    public static VaccinationCardModel vacinationCardToVacinationCardModel(VaccinationCard vaccinationCard) {
        return new VaccinationCardModel(vaccinationCard.getId(),  vaccinationCard.getEmail(),
                vaccinationCard.getFrontCard(), vaccinationCard.getBackCard(),
                StatusModel.valueOf(vaccinationCard.getStatus().name()), "");
    }

    public static VaccinationCard vacinationCardModelToVacinationCard(VaccinationCardModel vaccinationCardModel) {
        return new VaccinationCard(vaccinationCardModel.getId(),  vaccinationCardModel.getEmail(),
                vaccinationCardModel.getFrontCard(), vaccinationCardModel.getBackCard(),
                Status.valueOf(vaccinationCardModel.getStatusModel().name()));
    }

    public static VaccinationCard toDomain(VaccinationCardRequest request) {
        try {
            return new VaccinationCard(
                    request.email(),
                    request.frontCard().getBytes(),
                    request.backCard().getBytes(),
                    Status.RECEIVED
            );
        } catch (IOException e) {
            throw new InvalidFileException("Erro ao processar arquivos do cartão de vacinação");
        }
    }

}
