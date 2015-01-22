package com.luxoft.bankapp.model.impl;

public class NotEnoughFundsException extends BankException {
    public String getMessage() {
        return "Not enough funds";
    }
}
