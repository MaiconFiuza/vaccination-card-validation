package com.fiuza.vacinnation_card_validation.infra.mappers;

import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;
import com.fiuza.vacinnation_card_validation.core.enums.Type;
import com.fiuza.vacinnation_card_validation.infra.model.TypeModel;
import com.fiuza.vacinnation_card_validation.infra.model.UserModel;
import com.fiuza.vacinnation_card_validation.infra.model.VaccinationModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VaccinationMapperTest {

    @Test
    void shouldMapVaccinationToVaccinationModel() {
        UUID vaccinationId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.of(2024, 9, 8);

        Vaccination vaccination = new Vaccination(
                vaccinationId,
                "Vacina Gripe",
                date,
                Type.PRIMEIRA_DOSE,
                new User(userId)
        );

        VaccinationModel vaccinationModel = VaccinationMapper.vacinationToVacinationModel(vaccination);

        assertNotNull(vaccinationModel);
        assertEquals(vaccination.getId(), vaccinationModel.getId());
        assertEquals(vaccination.getName(), vaccinationModel.getName());
        assertEquals(vaccination.getDate(), vaccinationModel.getDate());
        assertEquals(vaccination.getType().name(), vaccinationModel.getType().name());
        assertNotNull(vaccinationModel.getUser());
        assertEquals(vaccination.getUser().getId(), vaccinationModel.getUser().getId());
    }

    @Test
    void shouldMapVaccinationModelToVaccination() {
        UUID vaccinationId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.of(2024, 9, 8);

        VaccinationModel vaccinationModel = new VaccinationModel(
                vaccinationId,
                "Vacina Covid",
                date,
                TypeModel.PRIMEIRA_DOSE,
                new UserModel(userId)
        );

        Vaccination vaccination = VaccinationMapper.vacinationModelToVacination(vaccinationModel);

        assertNotNull(vaccination);
        assertEquals(vaccinationModel.getId(), vaccination.getId());
        assertEquals(vaccinationModel.getName(), vaccination.getName());
        assertEquals(vaccinationModel.getDate(), vaccination.getDate());
        assertEquals(vaccinationModel.getType().name(), vaccination.getType().name());
        assertNotNull(vaccination.getUser());
        assertEquals(vaccinationModel.getUser().getId(), vaccination.getUser().getId());
    }

    @Test
    void shouldHandleTypeConversionCorrectly() {
        // Given
        Type typeDomain = Type.PRIMEIRA_DOSE;
        TypeModel typeModel = TypeModel.PRIMEIRA_DOSE;

        // When
        VaccinationModel vaccinationModel = new VaccinationModel(
                UUID.randomUUID(),
                "Teste",
                LocalDate.now(),
                typeModel,
                new UserModel(UUID.randomUUID())
        );

        Vaccination vaccination = new Vaccination(
                UUID.randomUUID(),
                "Teste",
                LocalDate.now(),
                typeDomain,
                new User(UUID.randomUUID())
        );

        assertEquals(typeDomain.name(), vaccinationModel.getType().name());
        assertEquals(typeModel.name(), vaccination.getType().name());
    }
}