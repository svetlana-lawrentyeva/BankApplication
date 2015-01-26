package com.luxoft.bankapp.model;

import java.io.Serializable;

public enum Gender implements Serializable {
    MALE("Mr."), FEMALE("Mrs.");

    private final String salutation;

    Gender(String salutation) {
        this.salutation = salutation;
    }

    public String getSalutation() {
        return salutation;
    }
}
