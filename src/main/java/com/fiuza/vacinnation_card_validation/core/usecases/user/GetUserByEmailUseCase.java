package com.fiuza.vacinnation_card_validation.core.usecases.user;

import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.core.gateway.UserGateway;

import java.util.Optional;

public class GetUserByEmailUseCase {
    private final UserGateway userGateway;

    public GetUserByEmailUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public Optional<User> execute(String email) {
        return userGateway.findUserEmail(email);
    }
}
