package com.luxoft.bankapp.model.impl;

/**
 * Created by SCJP on 15.01.15.
 */
public class ClientNotExistsException extends BankException {
    public String getMessage(){
        return "Client doesn't exists";
    }
}
