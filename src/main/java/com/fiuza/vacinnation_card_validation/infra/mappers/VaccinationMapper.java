package com.fiuza.vacinnation_card_validation.infra.mappers;

import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;
import com.fiuza.vacinnation_card_validation.core.enums.Type;
import com.fiuza.vacinnation_card_validation.infra.model.TypeModel;
import com.fiuza.vacinnation_card_validation.infra.model.UserModel;
import com.fiuza.vacinnation_card_validation.infra.model.VaccinationModel;

public class VaccinationMapper {
    public static VaccinationModel vacinationToVacinationModel(Vaccination vaccination) {
        return new VaccinationModel(
                vaccination.getId(),
                vaccination.getName(),
                vaccination.getDate(),
                typeToTypeModel(vaccination.getType()),
                new UserModel(vaccination.getUser().getId())
        );
    }

    public static Vaccination vacinationModelToVacination(VaccinationModel vaccinationModel) {
        return new Vaccination(
                vaccinationModel.getId(),
                vaccinationModel.getName(),
                vaccinationModel.getDate(),
                typeModelToType(vaccinationModel.getType()),
                new User(vaccinationModel.getUser().getId())
        );
    }


    private static TypeModel typeToTypeModel(Type type) {
        return TypeModel.valueOf(type.name());
    }

    private static Type typeModelToType(TypeModel typeModel) {
        return Type.valueOf(typeModel.name());
    }

}
