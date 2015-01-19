package com.luxoft.bankapp.model.impl;

import java.util.Map;

/**
 * Created by SCJP on 14.01.15.
 */
public class CheckingAccount extends AbstractAccount {
    private float overdraft = 10;

    public CheckingAccount(){}
    public CheckingAccount(float startBalance){
        super.setBalance(startBalance);
    }

    public CheckingAccount(float startBalance, float overdraft){
        super.setBalance(startBalance);
        this.setOverdraft(overdraft);
    }
    public void deposit(float x) {
        balance+=x;
    }

    @Override
    public void withdraw(float x) throws OverDraftLimitExceededException {
        if(balance+overdraft>=x)
            balance-=x;
        else{
            float money = getAvailableMoney();
            throw  new OverDraftLimitExceededException(this, money);
        }
    }

    @Override
    public float getAvailableMoney() {
        return balance+overdraft;
    }

    @Override
    public void parseFeed(Map<String, String> feed) {
        this.balance = Float.parseFloat("balance");
        this.overdraft = Float.parseFloat("overdraft");
    }

    @Override
    public void printReport() {
        System.out.println(this);
    }

    public float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(float overdraft) {
        if (overdraft<0) throw new IllegalArgumentException();
        this.overdraft = overdraft;
    }

    public String toString(){
        return "Checking account "+this.hashCode()+" with balance: "+balance+", overdraft: "+overdraft;
    }

}
