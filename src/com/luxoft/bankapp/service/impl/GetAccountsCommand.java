package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.service.Command;

/**
 * Created by SCJP on 15.01.15.
 */
public class GetAccountsCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Accounts of "+ BankCommander.currentClient);
        System.out.println(bankService.getAccounts(BankCommander.currentClient));
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get accounts");
    }
}
