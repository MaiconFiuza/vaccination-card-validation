package com.fiuza.vacinnation_card_validation.core.dto.response;

import com.fiuza.vacinnation_card_validation.core.entities.User;

import java.util.List;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        List<VaccinationResponse> vaccination

) {
    public static  UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getVaccinationList()
                        .stream()
                        .map(vaccination ->   VaccinationResponse.fromDomain(vaccination))
                        .toList()
        );
    }
}
