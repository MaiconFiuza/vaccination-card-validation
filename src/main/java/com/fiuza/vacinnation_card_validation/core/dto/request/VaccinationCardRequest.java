package com.fiuza.vacinnation_card_validation.core.dto.request;


import org.springframework.web.multipart.MultipartFile;


public record VaccinationCardRequest(
        String email,
        MultipartFile frontCard,
        MultipartFile backCard
) {
}