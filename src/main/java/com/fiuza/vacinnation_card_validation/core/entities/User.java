package com.fiuza.vacinnation_card_validation.core.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID id;
    private String cpf;
    private String name;
    private String cns;
    private String email;
    private String phone;
    private LocalDate birthdate;
    private List<Vaccination> vaccinationList;

    public User(UUID id, String cpf, String name, String cns, String email, String phone, LocalDate birthdate,
                List<Vaccination> vaccinationList) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.cns = cns;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
        this.vaccinationList = vaccinationList;
    }

    public User(UUID id, String cpf, String name, String cns, String email, String phone, LocalDate birthdate) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.cns = cns;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
    }

    public User(){}

    public User(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCns() {
        return cns;
    }

    public void setCns(String cns) {
        this.cns = cns;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Vaccination> getVaccinationList() {
        return vaccinationList;
    }

    public void setVaccinationList(List<Vaccination> vaccinationList) {
        this.vaccinationList = vaccinationList;
    }
}
