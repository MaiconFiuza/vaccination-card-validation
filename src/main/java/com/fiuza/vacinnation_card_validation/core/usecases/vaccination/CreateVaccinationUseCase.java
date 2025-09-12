package com.fiuza.vacinnation_card_validation.core.usecases.vaccination;

import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;
import com.fiuza.vacinnation_card_validation.core.exceptions.NotFoundException;
import com.fiuza.vacinnation_card_validation.core.gateway.UserGateway;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationGateway;

import java.util.UUID;

public class CreateVaccinationUseCase {
    private VaccinationGateway vaccinationGateway;
    private UserGateway userGateway;

    public CreateVaccinationUseCase(VaccinationGateway vaccinationGateway, UserGateway userGateway) {
        this.userGateway = userGateway;
        this.vaccinationGateway = vaccinationGateway;
    }

    public Vaccination execute(Vaccination vaccination, UUID id) {
        var user = userGateway.findUserById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        Vaccination vaccinationUpdated = new Vaccination(vaccination.getId(),
                vaccination.getName(), vaccination.getDate(), vaccination.getType(), user);
        return vaccinationGateway.create(vaccinationUpdated);
    }
}
