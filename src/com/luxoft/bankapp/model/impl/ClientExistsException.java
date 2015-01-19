package com.luxoft.bankapp.model.impl;

/**
 * Created by SCJP on 15.01.15.
 */
public class ClientExistsException extends BankException {
    public String getMessage(){
        return "Client already exists";
    }
}
