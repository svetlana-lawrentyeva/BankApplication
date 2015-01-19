package com.luxoft.bankapp.model;

/**
 * Created by SCJP on 14.01.15.
 */
public enum  Gender{
    MALE("Mr."), FEMALE("Mrs.");

    private final String salutation;

    Gender(String salutation){
        this.salutation = salutation;
    }
    public String getSalutation(){
        return salutation;
    }
}
