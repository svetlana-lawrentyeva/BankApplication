package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.exceptions.OverDraftLimitExceededException;

import java.util.Map;

public class CheckingAccount extends AbstractAccount {
    private float overdraft = 10;

    @Override
    public float getOverdraft() {
        return overdraft;
    }

    @Override
    public void setOverdraft(float overdraft) {
        if (overdraft < 0) {
            throw new IllegalArgumentException();
        }
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

    @Override public int hashCode() {
        return (int) (getClient().hashCode()+getBalance()+overdraft);
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        CheckingAccount account = (CheckingAccount) obj;
        return (this.getBalance() == account.getBalance() &&
                this.overdraft == account.overdraft &&
                this.getClient().equals(account.getClient()));
    }

    //--------------------------------------------------

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
    public String printReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n").append("Checking account #").append(getId()).append(". balance: ");
        builder.append(getBalance()).append(", overdraft: ").append(overdraft);
        System.out.println(builder);
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Checking account #").append(getId());
        return builder.toString();
    }

}
