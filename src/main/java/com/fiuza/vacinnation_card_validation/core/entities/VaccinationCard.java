package com.fiuza.vacinnation_card_validation.core.entities;

import com.fiuza.vacinnation_card_validation.core.enums.Status;

import java.util.UUID;

public class VaccinationCard {
    private UUID id;
    private String email;
    private byte[] frontCard;
    private byte[] backCard;
    private Status status;
    private String message;

    public VaccinationCard(UUID id, String email, byte[] frontCard, byte[] backCard, Status status, String message) {
        this.id = id;
        this.email = email;
        this.frontCard = frontCard;
        this.backCard = backCard;
        this.status = status;
        this.message = message;
    }

    public VaccinationCard(UUID id, String email, byte[] frontCard, byte[] backCard, Status status) {
        this.id = id;
        this.email = email;
        this.frontCard = frontCard;
        this.backCard = backCard;
        this.status = status;
        this.message = "";
    }

    public VaccinationCard (String email, byte[] frontCard, byte[] backCard, Status status) {
        this.email = email;
        this.frontCard = frontCard;
        this.backCard = backCard;
        this.status = status;
        this.message = "";
    }

    public VaccinationCard(){}


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getFrontCard() {
        return frontCard;
    }

    public void setFrontCard(byte[] frontCard) {
        this.frontCard = frontCard;
    }

    public byte[] getBackCard() {
        return backCard;
    }

    public void setBackCard(byte[] backCard) {
        this.backCard = backCard;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
