package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.impl.*;

import java.util.List;

/**
 * Created by SCJP on 14.01.15.
 */
public class BankService {

    public void addClient(Bank bank, Client client) throws ClientExistsException {
        bank.addClient(client);
    }

    public void removeClient(Bank bank, Client client){
        bank.removeClient(client);
    }

    public void addAccount(Client client, Account account){
        client.addAccount(account);
    }

    public void setActiveAccount(Client client, Account account){
        client.setActiveAccount(account);
    }

    public void withdraw(Client client, float x) throws BankException {
        client.withdraw(x);
    }

    public Client findClient(String name) throws ClientNotExistsException {
        return BankCommander.currentBank.findClient(name);
    }

    public String getAccounts(Client client) {
        return client.getAccountsInfo();
    }

    public void deposit(Client client, float x) {
        client.deposit(x);
    }

    public String getAccountInfo(Account account) {
        return account.toString();
    }

    public void transfer(Client client1, Client c2, float x) throws BankException {
        client1.transfer(c2, x);
    }

    public Client getClient(Bank bank, String clientName) {
        return bank.getClientsMapByName().get(clientName);
    }
}
