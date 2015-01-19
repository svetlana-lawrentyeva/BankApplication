package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;

import java.util.*;

/**
 * Created by SCJP on 14.01.15.
 */
public class ClientImpl implements com.luxoft.bankapp.model.Client {
    private String name;
    private String city;
    private String email;
    private String phone;
    private float overdraft;
    private float balance;
    private Gender gender;
    private Set<Account> accounts = new HashSet<Account>();
    private Account activeAccount;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ClientImpl(){}

    public ClientImpl(String city){
        this.city = city;
    }

    public ClientImpl(float initialOverdraft, Gender gender){
        this.overdraft = initialOverdraft;
        this.gender = gender;
    }

    @Override
    public float getBalance(){
        return this.balance;
    }

    @Override
    public void deposit(float x){
        activeAccount.deposit(x);
    }

    @Override
    public void withdraw(float x) throws BankException {
        activeAccount.withdraw(x);
    }

    @Override
    public void withdraw(float x, Account account) throws BankException {

        if(hasAccount(account)==false) throw new IllegalArgumentException("The Client has not account "+account);
        account.withdraw(x);
    }

    private boolean hasAccount(Account account){
        boolean hasAccount=false;
        for(Account a:accounts){
            if(a.equals(account)){
                hasAccount=true;
                break;
            }
        }
        return hasAccount;
    }

    @Override
    public Account createAccount(AccountType accountType){
        Account account=null;
        if(accountType.equals(AccountType.CHECKING_ACCOUNT))
            account = new CheckingAccount(0);
        else if(accountType.equals(AccountType.SAVING_ACCOUNT))
            account = new SavingAccount(0);
        accounts.add(account);
        account.setId(account.hashCode()* new Date().getTime());
        return account;
    }
    @Override
    public Account createAccount(AccountType accountType, float initialBalance){
        Account account=null;
        if(accountType.equals(AccountType.CHECKING_ACCOUNT))
            account = new CheckingAccount(initialBalance, overdraft);
        else if(accountType.equals(AccountType.SAVING_ACCOUNT))
            account = new SavingAccount(initialBalance);
        accounts.add(account);
        account.setId(account.hashCode() * new Date().getTime());
        return account;
    }

    @Override
    public void printReport() {
        System.out.println(this);

        System.out.println(getAccountsInfo());
    }

    public String toString(){
        return gender.getSalutation()+ " " +name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<Account> getAccounts() {
        return accounts;
    }

    @Override
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public Account getActiveAccount() {
        return activeAccount;
    }

    @Override
    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    @Override
    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getClientSalutation(){
        return gender.getSalutation();
    }

    @Override
    public String getAccountsInfo() {
        StringBuilder builder = new StringBuilder();
        for(Account account:accounts){
            if(account.equals(activeAccount))
                builder.append("active: ");
            builder.append(account).append("\n");
        }
        return builder.toString();
    }

    @Override
    public void transfer(Client c2, float x) throws BankException {
        try {
            this.withdraw(x);
            c2.deposit(x);
        } catch (BankException e) {
            throw e;
        }
    }

    public int hashcode(){
        int p=17;
        int q=37;
        return (p * q + name.hashCode()) * q + gender.hashCode();
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        ClientImpl client = (ClientImpl)obj;
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

    @Override
    public String getFullInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("\n").append(email).append("\n").append(phone).append("\n");
        builder.append("balance: ").append("overdraft: ").append(overdraft).append("\n");
        builder.append(getAccountsInfo());
        return builder.toString();
    }

    @Override
    public void parseFeed(Map<String, String> feed) {
        try{
            String gen = feed.get("gender");
            if(gen.equals("m")) this.gender = Gender.MALE;
            else if(gen.equals("f")) this.gender = Gender.FEMALE;
            else throw new FeedException();
            this.name = feed.get("name");
            this.overdraft = Float.parseFloat(feed.get("overdraft"));
            this.setBalance(Float.parseFloat(feed.get("balance")));
            String accountType = feed.get("accountType");
            if(accountType.equals("c"))this.activeAccount = new CheckingAccount();
            else if(accountType.equals("s"))this.activeAccount = new SavingAccount();
            else throw  new FeedException();
            this.activeAccount.parseFeed(feed);
        } catch (Exception ex){
            throw new FeedException();
        }
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
