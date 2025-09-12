package com.fiuza.vacinnation_card_validation.core.gateway;

import com.fiuza.vacinnation_card_validation.core.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {

    Optional<User> findUserById(UUID id);

    Optional<User> findUserEmail(String email);

    boolean hasUser(String email);

}
