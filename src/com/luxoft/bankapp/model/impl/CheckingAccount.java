package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.exceptions.OverDraftLimitExceededException;

import java.util.Map;

public class CheckingAccount extends AbstractAccount {
    private float overdraft = 10;

    public CheckingAccount() {
    }

    public CheckingAccount(float startBalance) {
        super(startBalance);
    }

    public CheckingAccount(float startBalance, float overdraft) {
        setBalance(startBalance);
        this.overdraft = overdraft;
    }

    @Override
    public void setBalance(float balance) {
        if(balance < overdraft * -1){
            throw new IllegalArgumentException();
        }
        else {
            super.setBalance(balance);
        }
    }

    public void deposit(float x) {
        if(x<0) throw new IllegalArgumentException();
        setBalance(getBalance() + x);
    }

    @Override
    public void withdraw(float x) throws OverDraftLimitExceededException {
        if(x<0) throw new IllegalArgumentException();
        if (getBalance() + overdraft >= x) {
            setBalance(getBalance()-x);
        } else {
            float money = getAvailableMoney();
            throw new OverDraftLimitExceededException(this, money);
        }
    }

    @Override
    public float getAvailableMoney() {
        return getBalance() + overdraft;
    }

    @Override
    public void parseFeed(Map<String, String> feed) {
        setBalance(Float.parseFloat(feed.get("balance")));
        this.overdraft = Float.parseFloat(feed.get("overdraft"));
    }

    @Override
    public void printReport() {
        System.out.println(this);
    }

    public float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(float overdraft) {
        if (overdraft < 0) {
            throw new IllegalArgumentException();
        }
        this.overdraft = overdraft;
    }

    public String toString() {
        return "Checking account " + getId() + " with balance: " + getBalance() + ", overdraft: " + overdraft;
    }

}
