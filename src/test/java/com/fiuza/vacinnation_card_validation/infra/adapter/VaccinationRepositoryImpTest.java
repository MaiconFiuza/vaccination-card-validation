package com.fiuza.vacinnation_card_validation.infra.adapter;

import com.fiuza.vacinnation_card_validation.core.entities.User;
import com.fiuza.vacinnation_card_validation.core.entities.Vaccination;
import com.fiuza.vacinnation_card_validation.core.enums.Type;
import com.fiuza.vacinnation_card_validation.infra.model.TypeModel;
import com.fiuza.vacinnation_card_validation.infra.model.UserModel;
import com.fiuza.vacinnation_card_validation.infra.model.VaccinationModel;
import com.fiuza.vacinnation_card_validation.infra.repositories.VaccinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VaccinationRepositoryImpTest {

    @Mock
    private VaccinationRepository vaccinationRepository;

    @InjectMocks
    private VaccinationRepositoryImp vaccinationRepositoryImp;

    private UUID vaccinationId;
    private Vaccination vaccination;
    private VaccinationModel vaccinationModel;
    private User user;
    private UserModel userModel;

    @BeforeEach
    void setUp() {
        vaccinationId = UUID.randomUUID();

        user = new User(vaccinationId);
        user.setId(UUID.randomUUID());

        userModel = new UserModel(user.getId());

        vaccination = new Vaccination(
                vaccinationId,
                "Hepatite B",
                LocalDate.of(2024, 9, 9),
                Type.PRIMEIRA_DOSE,
                user
        );

        vaccinationModel = new VaccinationModel(
                vaccinationId,
                "Hepatite B",
                LocalDate.of(2024, 9, 9),
                TypeModel.PRIMEIRA_DOSE,
                userModel
        );
    }

    @Test
    void shouldFindVaccinationByIdWhenExists() {
        when(vaccinationRepository.findById(vaccinationId)).thenReturn(Optional.of(vaccinationModel));

        Optional<Vaccination> result = vaccinationRepositoryImp.findById(vaccinationId);

        assertTrue(result.isPresent());
        assertEquals(vaccinationId, result.get().getId());
        assertEquals(vaccination.getName(), result.get().getName());
        assertEquals(vaccination.getDate(), result.get().getDate());
        assertEquals(vaccination.getType(), result.get().getType());
        assertEquals(vaccination.getUser().getId(), result.get().getUser().getId());

        verify(vaccinationRepository, times(1)).findById(vaccinationId);
    }

    @Test
    void shouldReturnEmptyWhenVaccinationByIdDoesNotExist() {
        when(vaccinationRepository.findById(vaccinationId)).thenReturn(Optional.empty());

        Optional<Vaccination> result = vaccinationRepositoryImp.findById(vaccinationId);

        assertFalse(result.isPresent());
        verify(vaccinationRepository, times(1)).findById(vaccinationId);
    }

    @Test
    void shouldCreateVaccinationSuccessfully() {
        when(vaccinationRepository.save(any(VaccinationModel.class))).thenReturn(vaccinationModel);

        Vaccination result = vaccinationRepositoryImp.create(vaccination);

        assertNotNull(result);
        assertEquals(vaccination.getId(), result.getId());
        assertEquals(vaccination.getName(), result.getName());
        assertEquals(vaccination.getDate(), result.getDate());
        assertEquals(vaccination.getType(), result.getType());
        assertEquals(vaccination.getUser().getId(), result.getUser().getId());

        verify(vaccinationRepository, times(1)).save(any(VaccinationModel.class));
    }
}
