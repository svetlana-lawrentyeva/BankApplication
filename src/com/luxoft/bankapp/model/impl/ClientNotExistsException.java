package com.luxoft.bankapp.model.impl;

public class ClientNotExistsException extends BankException {
    public String getMessage() {
        return "Client doesn't exists";
    }
}
