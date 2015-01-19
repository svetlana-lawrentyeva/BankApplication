package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.impl.NotEnoughFundsException;
import com.luxoft.bankapp.model.impl.OverDraftLimitExceededException;

import java.util.Map;

/**
 * Created by SCJP on 14.01.15.
 */
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
