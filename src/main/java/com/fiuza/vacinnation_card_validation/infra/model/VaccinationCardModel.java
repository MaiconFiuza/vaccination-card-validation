package com.fiuza.vacinnation_card_validation.infra.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vaccination_card")
public class VaccinationCardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private byte[] frontCard;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private byte[] backCard;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusModel statusModel;

    private String message;
}