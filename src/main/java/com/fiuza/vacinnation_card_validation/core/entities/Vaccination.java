package com.fiuza.vacinnation_card_validation.core.entities;

import com.fiuza.vacinnation_card_validation.core.enums.Type;

import java.time.LocalDate;
import java.util.UUID;

public class Vaccination {
    private UUID id;
    private String name;
    private LocalDate date;
    private Type type;
    private User user;

    public Vaccination(UUID id, String name, LocalDate date, Type type, User user) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.type = type;
        this.user = user;
    }

    public Vaccination(String name, LocalDate date, Type type) {
        this.name = name;
        this.date = date;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
