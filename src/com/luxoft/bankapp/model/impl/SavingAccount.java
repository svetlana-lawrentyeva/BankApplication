package com.luxoft.bankapp.model.impl;

import java.util.Map;

/**
 * Created by SCJP on 14.01.15.
 */
public class SavingAccount extends AbstractAccount {

    public SavingAccount(){}
    public SavingAccount(float startBalance){
        super.setBalance(startBalance);
    }

    public float getBalance(){
        return super.getBalance();
    }
    @Override
    public void deposit(float x) {
        balance+=x;
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {
        if(balance<x) throw  new NotEnoughFundsException();
            balance-=x;
    }

    @Override
    public float getAvailableMoney() {
        return balance;
    }

    @Override
    public void parseFeed(Map<String, String> feed) {
        this.balance = Float.parseFloat(feed.get("balance"));
    }


    @Override
    public void printReport() {
        System.out.println(this);
    }

    public String toString(){
        return "Saving account "+this.hashCode()+" with balance: "+balance;
    }
}
