package com.fiuza.vacinnation_card_validation.infra.mappers;

import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;
import com.fiuza.vacinnation_card_validation.infra.model.UserModel;
import com.fiuza.vacinnation_card_validation.infra.model.VaccinationModel;

import java.util.List;

import static com.fiuza.vacinnation_card_validation.infra.mappers.VaccinationMapper.vacinationToVacinationModel;

public class UserMapper {
    public static UserModel userToUserModel(User user) {
        List<VaccinationModel> vacinations = user.getVaccinationList().stream()
                .map(vacination -> vacinationToVacinationModel(vacination)).toList();
        return new UserModel(user.getId(), user.getCpf(), user.getName(), user.getCns(), user.getEmail(),
                user.getPhone(), user.getBirthdate(), vacinations);
    }

    public static User userModelToUser(UserModel userModel) {
        List<Vaccination> vaccinations = userModel.getVacinations().stream()
                .map(VaccinationMapper::vacinationModelToVacination).toList();
        return new User(userModel.getId(), userModel.getCpf(), userModel.getName(), userModel.getCns(), userModel.getEmail(),
                userModel.getPhone(), userModel.getBirthdate(), vaccinations);
    }
}
