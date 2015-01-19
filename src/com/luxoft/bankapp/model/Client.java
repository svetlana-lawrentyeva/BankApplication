package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.impl.BankException;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by SCJP on 14.01.15.
 */
public interface Client extends Report, Serializable {
    float getBalance();

    void deposit(float x);

    void withdraw(float x) throws BankException;

    public void withdraw(float x, Account account) throws BankException;

    Account createAccount(AccountType accountType);

    Account createAccount(AccountType accountType, float initialBalance);

    String getCity();

    void setCity(String city);

    @Override
    void printReport();

    String getName();

    void setName(String name);

    Set<Account> getAccounts();

    void setAccounts(Set<Account> accounts);

    Account getActiveAccount();

    void setActiveAccount(Account activeAccount);

    void addAccount(Account account);

    public Gender getGender();

    public void setGender(Gender gender);

    public String getClientSalutation();

    String getAccountsInfo();

    void transfer(Client c2, float x) throws BankException;

    public String getEmail();

    public void setEmail(String email);

    public String getPhone();

    public void setPhone(String phone);

    public float getOverdraft();

    public void setOverdraft(float overdraft);

    String getFullInfo();

    void parseFeed(Map<String, String> feed);

    public void setBalance(float balance);
}
