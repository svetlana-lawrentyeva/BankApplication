package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;

import java.util.Map;

public class SavingAccount extends AbstractAccount {

    public SavingAccount() {
    }

    @Override
    public void setBalance(float balance) {
        if (balance < 0) {
            throw new IllegalArgumentException();
        }
        super.setBalance(balance);
    }

    @Override
    public float getOverdraft() {
        return 0;
    }

    @Override
    public void setOverdraft(float overdraft) {
    }

    //------------------------------------------------------

    @Override public int hashCode() {
        return (int) (getClient().hashCode() + getBalance());
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null){
            return false;
        }
        if (this.getClass() != obj.getClass()){
            return false;
        }
        SavingAccount account = (SavingAccount) obj;
        return (this.getBalance() == account.getBalance() &&
                this.getClient().equals(account.getClient()));
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {
        if(x<0) {
            throw new IllegalArgumentException();
        }
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
    public String printReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n").append("Saving account #").append(getId()).append(". balance: ").append(getBalance());
        System.out.println(builder);
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Saving account #").append(getId());
        return builder.toString();
    }
}
