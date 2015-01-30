package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;

public abstract class AbstractAccount implements Account {
    private long id = -1;
    private float balance;
    private Client client;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public float getBalance() {
        return balance;
    }

    @Override
    public void setBalance(float balance){
        this.balance = balance;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    //------------------------------------------------------

    @Override
    public void deposit(float x) {
        if (x < 0) throw new IllegalArgumentException();
        setBalance(getBalance() + x);
    }

    @Override
    public void transfer(Account account, float x) throws NotEnoughFundsException {
        if(getAvailableMoney()>x){
            withdraw(x);
            account.deposit(x);
        }
    }

    @Override
    public int decimalValue() {
        return Math.round(balance);
    }

    @Override
    public int hashCode() {
        int p = 17;
        int q = 37;
        return (int) ((p * q + super.hashCode()) * q);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        AbstractAccount account = (AbstractAccount) obj;
        return (this.id == account.id);
    }

}
