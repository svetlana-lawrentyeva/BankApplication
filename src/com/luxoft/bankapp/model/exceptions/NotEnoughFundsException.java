package com.luxoft.bankapp.model.exceptions;

public class NotEnoughFundsException extends BankException {
    public String getMessage() {
        return "Not enough funds";
    }
}
