package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Account;

public abstract class AbstractAccount implements Account {
    private long id;
    protected float balance;

    @Override
    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        if (balance < 0) throw new IllegalArgumentException();
        this.balance = balance;
    }

    @Override
    public int decimalValue() {
        return Math.round(balance);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int hashcode() {
        int p = 17;
        int q = 37;
        return (int) ((p * q + id) * q + id);
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        AbstractAccount account = (AbstractAccount) obj;
        return (this.id == account.id);
    }
}
