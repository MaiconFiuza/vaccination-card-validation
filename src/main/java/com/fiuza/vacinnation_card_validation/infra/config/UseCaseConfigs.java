package com.fiuza.vacinnation_card_validation.infra.config;

import com.fiuza.vacinnation_card_validation.core.gateway.UserGateway;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationCardGateway;
import com.fiuza.vacinnation_card_validation.core.gateway.VaccinationGateway;
import com.fiuza.vacinnation_card_validation.core.usecases.user.GetUserByEmailUseCase;
import com.fiuza.vacinnation_card_validation.core.usecases.vaccination.CreateVaccinationUseCase;
import com.fiuza.vacinnation_card_validation.core.usecases.vacinnation_card.*;
import com.fiuza.vacinnation_card_validation.infra.adapter.UserRepositoryImp;
import com.fiuza.vacinnation_card_validation.infra.adapter.VaccinationCardRepositoryImp;
import com.fiuza.vacinnation_card_validation.infra.adapter.VaccinationRepositoryImp;
import com.fiuza.vacinnation_card_validation.infra.repositories.UserRepository;
import com.fiuza.vacinnation_card_validation.infra.repositories.VaccinationCardRepository;
import com.fiuza.vacinnation_card_validation.infra.repositories.VaccinationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfigs {

    @Bean
    public UserGateway userGateway(UserRepository userRepository) {
        return new UserRepositoryImp(userRepository);
    }

    @Bean
    public VaccinationGateway vaccinationGateway(VaccinationRepository vaccinationRepository) {
        return new VaccinationRepositoryImp(vaccinationRepository);
    }

    @Bean
    VaccinationCardGateway vacinationCardGateway(VaccinationCardRepository vaccinationCardRepository) {
        return new VaccinationCardRepositoryImp(vaccinationCardRepository);
    }

    @Bean
    public SendVaccinationCardUseCase sendVacinationCardUseCase(
            VaccinationCardGateway vaccinationCardGateway, UserGateway userGateway) {
        return new SendVaccinationCardUseCase(vaccinationCardGateway, userGateway);
    }

    @Bean
    public GetVaccinationCardByIdUseCase getVaccinationCardByIdUseCase(VaccinationCardGateway vaccinationCardGateway) {
        return new GetVaccinationCardByIdUseCase(vaccinationCardGateway);
    }

    @Bean
    public GetVaccinationCardByEmailUseCase getVaccinationCardByEmailUseCase(
            VaccinationCardGateway vaccinationCardGateway
    ) {
        return new GetVaccinationCardByEmailUseCase(vaccinationCardGateway);
    }

    @Bean
    public UpdateVaccinationCardByIdUseCase updateVaccinationCardById(
            VaccinationCardGateway vaccinationCardGateway
    ) {
        return new UpdateVaccinationCardByIdUseCase(vaccinationCardGateway);
    }


    @Bean
    public GetUserByEmailUseCase getUserByEmailUseCase(
            UserGateway userGateway
    ) {
        return new GetUserByEmailUseCase(userGateway);
    }

    @Bean
    public CreateVaccinationUseCase createVaccinationUseCase(
            VaccinationGateway vaccinationGateway,
            UserGateway userGateway
    ) {
        return new CreateVaccinationUseCase(vaccinationGateway, userGateway);
    }

    @Bean
    public DeleteVaccinationCardByIdUseCase deleteVaccinationCardByIdUseCase(
            VaccinationCardGateway vaccinationCardGateway) {
        return new DeleteVaccinationCardByIdUseCase(vaccinationCardGateway);
    }

    @Bean
    public GetAllVaccinationCardByEmailUseCase getAllVaccinationCardByEmailUseCase(
            VaccinationCardGateway vaccinationCardGateway) {
        return new GetAllVaccinationCardByEmailUseCase(vaccinationCardGateway);
    }

}
