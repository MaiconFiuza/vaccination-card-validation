package com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card;

import com.fiuza.vacinnation_card_validation.core.entities.VaccinationCard;
import com.fiuza.vacinnation_card_validation.core.exceptions.AlreadyExistException;
import com.fiuza.vacinnation_card_validation.core.exceptions.NotFoundException;
import com.fiuza.vacinnation_card_validation.core.gateway.UserGateway;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;

public class SendVaccinationCardUseCase {
    private VaccinationCardGateway vaccinationCardGateway;
    private UserGateway userGateway;

    public SendVaccinationCardUseCase(
            VaccinationCardGateway vaccinationCardGateway,
            UserGateway userGateway
    ) {
        this.vaccinationCardGateway = vaccinationCardGateway;
        this.userGateway = userGateway;
    }

    public VaccinationCard execute(VaccinationCard vaccinationCard) {
        String email = vaccinationCard.getEmail();
        validations(email);

        return vaccinationCardGateway.create(vaccinationCard);
    }

    private void validations(String email) {
        if (!userGateway.hasUser(email)) {
            throw new NotFoundException("Usuário não encontrado. Por favor tente novamente");
        }

        boolean hasCardSaved = vaccinationCardGateway.findByEmail(email).isPresent();

        if(hasCardSaved) throw new AlreadyExistException("Solicação já existe");
    }
}
