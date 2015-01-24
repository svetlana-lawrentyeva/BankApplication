package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.Report;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.exceptions.FeedException;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Client implements Report {
    private String name;
    private String city = "none";
    private String email;
    private String phone;
    private float overdraft;
    private Gender gender;
    private Set<Account> accounts = new HashSet<>();
    private Account activeAccount;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Client() {
    }

    public Client(String city) {
        this.city = city;
    }

    public Client(float initialOverdraft, Gender gender) {
        this.overdraft = initialOverdraft;
        this.gender = gender;
    }

    public void deposit(float x) {
        if (accounts.size() == 0) {
            Account account = new SavingAccount(x);
            accounts.add(account);
            activeAccount = account;
        } else {
            activeAccount.deposit(x);
        }
    }

    public void withdraw(float x) throws BankException {
        if (activeAccount == null) {
            throw new IllegalArgumentException("The Client " + this.toString() + " has not account yet");
        }
        activeAccount.withdraw(x);
    }

    public void withdraw(float x, Account account) throws BankException {

        if (hasAccount(account) == false) {
            throw new IllegalArgumentException("The Client has not account " + account);
        }
        account.withdraw(x);
    }

    private boolean hasAccount(Account account) {
        boolean hasAccount = false;
        for (Account a : accounts) {
            if (a.equals(account)) {
                hasAccount = true;
                break;
            }
        }
        return hasAccount;
    }

    public Account createAccount(AccountType accountType) {
        return createAccount(accountType, 0);
    }

    public Account createAccount(AccountType accountType, float initialBalance) {
        Account account = null;
        if (accountType.equals(AccountType.CHECKING_ACCOUNT)) {
            account = new CheckingAccount(initialBalance, overdraft);
        } else if (accountType.equals(AccountType.SAVING_ACCOUNT)) {
            account = new SavingAccount(initialBalance);
        }
        accounts.add(account);
        account.setId(account.hashCode() * new Date().getTime());
        return account;
    }

    public void printReport() {
        System.out.println(this);

        System.out.println(getAccountsInfo());
    }

    public String toString() {
        return gender.getSalutation() + " " + name + "  " + activeAccount.getBalance();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getClientSalutation() {
        return gender.getSalutation() + " " + name;
    }

    public String getAccountsInfo() {
        StringBuilder builder = new StringBuilder();
        for (Account account : accounts) {
            if (account.equals(activeAccount)) {
                builder.append("active: ");
            }
            builder.append(account).append("\n");
        }
        return builder.toString();
    }

    public void transfer(Client c2, float x) throws BankException {
        try {
            this.withdraw(x);
            c2.deposit(x);
        } catch (BankException e) {
            throw e;
        }
    }

    public int hashcode() {
        int p = 17;
        int q = 37;
        return (p * q + name.hashCode()) * q + gender.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Client client = (Client) obj;
        return (this.name.equals(client.name) && this.gender.equals(client.gender));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(float overdraft) {
        this.overdraft = overdraft;
    }

    public String getFullInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("\n").append(email).append("\n").append(phone).append("\n");
        builder.append("balance: ").append("overdraft: ").append(overdraft).append("\n");
        builder.append(getAccountsInfo());
        return builder.toString();
    }

    public void parseFeed(Map<String, String> feed) {
        try {
            String gen = feed.get("gender");
            if (gen.equals("m")) {
                this.gender = Gender.MALE;
            } else if (gen.equals("f")) {
                this.gender = Gender.FEMALE;
            } else {
                throw new FeedException("wrong parameter: gender");
            }
            this.setCity(feed.get("city"));
            this.name = feed.get("name");
            this.overdraft = Float.parseFloat(feed.get("overdraft"));
            String accountType = feed.get("accounttype");
            if (accountType.equals("c")) {
                this.activeAccount = new CheckingAccount();
            } else if (accountType.equals("s")) {
                this.activeAccount = new SavingAccount();
            } else {
                throw new FeedException("wrong parameter: account type");
            }
            this.accounts.add(this.activeAccount);
            this.activeAccount.parseFeed(feed);
        } catch (Exception ex) {
            throw new FeedException(ex.getMessage());
        }
    }

    public float getBalance(){
        return activeAccount.getBalance();
    }
}
