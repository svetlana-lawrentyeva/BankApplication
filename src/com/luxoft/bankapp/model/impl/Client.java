package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.Report;
import com.luxoft.bankapp.model.exceptions.FeedException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Client implements Report {
    private long id = -1;
    private String name = "";
    private String city = "";
    private String email = "";
    private String phone = "";
    private float overdraft;
    private Gender gender = null;
    private Bank bank = null;
    private Set<Account> accounts = new HashSet<>();
    private Account activeAccount = null;
    private AccountRegistrationListener listener = new OverdraftSetterListener();

    private static class OverdraftSetterListener implements AccountRegistrationListener{

        @Override
        public void onAccountAdded(Client client, Account account) {
            if(account instanceof CheckingAccount){
                account.setOverdraft(client.overdraft);
            }
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        boolean result = false;
        String NAME_PATTERN = "[a-zA-z]+([ '-][a-zA-Z]+)*";
        if (name.matches(NAME_PATTERN)) {
            this.name = name;
            result = true;
        }
        return result;
    }

    public void  addAccount(Account account) {
        accounts.add(account);
        account.setClient(this);
        listener.onAccountAdded(this, account);
    }

    public Set<Account> getAccounts(){
        return Collections.unmodifiableSet(accounts);
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
        for(Account account:accounts){
            listener.onAccountAdded(this, account);
        }
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        boolean result = false;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (email.matches(EMAIL_PATTERN)) {
            this.email = email;
            result = true;
        }
        return result;
    }

    public String getPhone() {
        return phone;
    }

    public boolean setPhone(String phone) {
        boolean result = false;
        String PHONE_PATTERN = "\\d{3}-\\d{7}";
        if (phone.matches(PHONE_PATTERN)) {
            this.phone = phone;
            result = true;
        }
        return result;
    }

    public float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(float overdraft) {
        this.overdraft = overdraft;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    //----------------------------------------------------------------

    /**
     * Get gender salutation and client's name
     */
    public String getClientSalutation() {
        return gender.getSalutation() + " " + name;
    }

    /**
     * Parse feed map to load data
     * @param feed map for parsing for get data
     */
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

    @Override
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

    @Override
    public int hashCode() {
        int p = 17;
        int q = 37;
        return (p * q + name.hashCode()) * q + gender.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("client #").append(id).append(" ").append(getClientSalutation());
        return builder.toString();
    }

    @Override public String printReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nclient #").append(id).append(" ").append(getClientSalutation()).append("\n");
        if (accounts.size() > 0) {
            builder.append("accounts:");
            builder.append("\n............................");
            for (Account account : accounts) {
                builder.append(account);
                if (activeAccount != null & account.equals(activeAccount)) {
                    builder.append(" < =====active=====");
                }
                builder.append("\n............................");
            }
        }
        System.out.println(builder);
        return builder.toString();
    }

    public float getBalance(){
        float balance = 0;
        for(Account account:accounts){
            balance += account.getBalance();
        }
        return balance;
    }
}
