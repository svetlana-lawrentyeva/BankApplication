package com.luxoft.bankapp.model.exceptions;

public class BankException extends Exception {
    public BankException(){}

    public BankException(String message){
        super(message);
    }
}
