package com.luxoft.bankapp.model.exceptions;

public class ClientNotExistsException extends BankException {
    public String getMessage() {
        return "Client doesn't exists";
    }
}
