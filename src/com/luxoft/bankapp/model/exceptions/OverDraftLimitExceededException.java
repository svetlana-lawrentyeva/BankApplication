package com.luxoft.bankapp.model.exceptions;

import com.luxoft.bankapp.model.Account;

public class OverDraftLimitExceededException extends NotEnoughFundsException {
    private Account account;
    private float availableMoney;

    public OverDraftLimitExceededException(Account account, float x) {
        this.account = account;
        availableMoney = x;

    }

    public String getMessage() {
        return "Over draft limit exceeded. Available money: " + availableMoney;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public float getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(float availableMoney) {
        this.availableMoney = availableMoney;
    }
}
