package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;

import java.util.Map;

public interface Account extends Report {
    public long getId();

    public void setId(long id);

    float getBalance();

    void deposit(float x);

    void withdraw(float x) throws NotEnoughFundsException;

    float getAvailableMoney();

    int decimalValue();

    void parseFeed(Map<String, String> feed);
}
