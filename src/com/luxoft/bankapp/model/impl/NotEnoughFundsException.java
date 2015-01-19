package com.luxoft.bankapp.model.impl;

/**
 * Created by SCJP on 15.01.15.
 */
public class NotEnoughFundsException extends BankException {
    public String getMessage(){
        return "Not enough funds";
    }
}
