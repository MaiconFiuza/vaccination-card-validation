package com.fiuza.vacinnation_card_validation.infra.adapter;

import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.core.gateway.UserGateway;
import com.fiuza.vacinnation_card_validation.infra.mappers.UserMapper;
import com.fiuza.vacinnation_card_validation.infra.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImp implements UserGateway {
    private final UserRepository userRepository;

    public UserRepositoryImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        return userRepository.findById(id)
                .map(UserMapper::userModelToUser);
    }

    @Override
    public Optional<User> findUserEmail(String email) {
        return userRepository.getUserModelByEmail(email)
                .map(UserMapper::userModelToUser);
    }

    @Override
    public boolean hasUser(String email) {
        return userRepository.getUserModelByEmail(email).isPresent();
    }
}
