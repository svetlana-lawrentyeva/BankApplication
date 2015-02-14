package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.impl.Client;

import java.util.Map;

public interface Account extends Report, MyClass {

    long getId();

    void setId(long id);

    float getBalance();

    void setBalance(float balance);

    float getOverdraft();

    void setOverdraft(float overdraft);

    Client getClient();

    void setClient(Client client);

    //---------------------------------------------

    /**
     * Deposit money to account balance
     * @param x money to deposit
     */
    void deposit(float x);

    /**
     * Withdraw money from account balance
     * @param x money to withdraw
     */
    void withdraw(float x) throws NotEnoughFundsException;

    /**
     * Transfer money from current account to another account
     * @param account account money will be transferred to
     * @param x money for transfer
     */
    void transfer(Account account, float x) throws NotEnoughFundsException;

    /**
     * Get available money
     */
    float getAvailableMoney();

    /**
     * Get balance like decimal
     */
    int decimalValue();

    /**
     * Parse feed map to load data
     * @param feed map for parsing to get data
     */
    void parseFeed(Map<String, String> feed);
}
