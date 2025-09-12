package com.fiuza.vacinnation_card_validation.infra.repositories;

import com.fiuza.vacinnation_card_validation.infra.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> getUserModelByEmail(String email);
}
