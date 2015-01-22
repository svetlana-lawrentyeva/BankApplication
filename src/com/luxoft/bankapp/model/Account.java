package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.impl.NotEnoughFundsException;

import java.io.Serializable;
import java.util.Map;

public interface Account extends Report, Serializable {
    public long getId();

    public void setId(long id);

    float getBalance();

    void deposit(float x);

    void withdraw(float x) throws NotEnoughFundsException;

    float getAvailableMoney();

    int decimalValue();

    void parseFeed(Map<String, String> feed);
}
