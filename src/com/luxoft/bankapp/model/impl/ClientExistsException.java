package com.luxoft.bankapp.model.impl;

public class ClientExistsException extends BankException {
    public String getMessage() {
        return "Client already exists";
    }
}
