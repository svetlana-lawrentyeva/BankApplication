package com.luxoft.bankapp.model.exceptions;

public class ClientExistsException extends BankException {
    public String getMessage() {
        return "Client already exists";
    }
}
