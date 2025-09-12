package com.fiuza.vacinnation_card_validation.infra.model;

public enum TypeModel {
    REFORÇO("REFORÇO"),
    PRIMEIRA_DOSE("1ª DOSE"),
    SEGUNDA_DOSE("2ª DOSE"),
    TERCEIRA_DOSE("3ª DOSE"),
    QUARTA_DOSE("4ª DOSE"),
    QUINTA_DOSE("5ª DOSE");

    private final String description;

    TypeModel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
