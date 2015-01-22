package com.luxoft.bankapp.model;

public enum Gender {
    MALE("Mr."), FEMALE("Mrs.");

    private final String salutation;

    Gender(String salutation) {
        this.salutation = salutation;
    }

    public String getSalutation() {
        return salutation;
    }
}
