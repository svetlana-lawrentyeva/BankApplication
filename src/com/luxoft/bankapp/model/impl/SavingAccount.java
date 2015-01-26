package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;

import java.util.Map;

public class SavingAccount extends AbstractAccount {

    public SavingAccount() {
    }

    public SavingAccount(float startBalance) {
        super(startBalance);
    }

    @Override
    public void deposit(float x) {
        if(x<0) throw new IllegalArgumentException();
        setBalance(getBalance()+x);
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {
        if(x<0) throw new IllegalArgumentException();
        if (getBalance() < x) {
            throw new NotEnoughFundsException();
        }
        setBalance(getBalance()-x);
    }

    @Override
    public float getAvailableMoney() {
        return getBalance();
    }

    @Override
    public void parseFeed(Map<String, String> feed) {
        setBalance(Float.parseFloat(feed.get("balance")));
    }

    @Override
    public void setBalance(float balance) {
        if (balance < 0) throw new IllegalArgumentException();
        super.setBalance(balance);
    }

    @Override
    public void printReport() {
        System.out.println(this);
    }

    public String toString() {
        return "Saving account " + getId() + " with balance: " + getBalance();
    }
}
