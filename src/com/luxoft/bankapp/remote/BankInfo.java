package com.luxoft.bankapp.remote;

import com.luxoft.bankapp.model.impl.Client;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BankInfo {
    private int getNumberOfClients;
    private int clientsNumber;
    private int getAccountsNumber;
    private Set<Client> getClientsSorted;
    private float getBankCreditSum;
    private Map<String, List<Client>> getClientsByCity;

    public int getGetNumberOfClients() {
        return getNumberOfClients;
    }

    public void setGetNumberOfClients(int getNumberOfClients) {
        this.getNumberOfClients = getNumberOfClients;
    }

    public int getClientsNumber() {
        return clientsNumber;
    }

    public void setClientsNumber(int clientsNumber) {
        this.clientsNumber = clientsNumber;
    }

    public int getGetAccountsNumber() {
        return getAccountsNumber;
    }

    public void setGetAccountsNumber(int getAccountsNumber) {
        this.getAccountsNumber = getAccountsNumber;
    }

    public Set<Client> getGetClientsSorted() {
        return getClientsSorted;
    }

    public void setGetClientsSorted(Set<Client> getClientsSorted) {
        this.getClientsSorted = getClientsSorted;
    }

    public float getGetBankCreditSum() {
        return getBankCreditSum;
    }

    public void setGetBankCreditSum(float getBankCreditSum) {
        this.getBankCreditSum = getBankCreditSum;
    }

    public Map<String, List<Client>> getGetClientsByCity() {
        return getClientsByCity;
    }

    public void setGetClientsByCity(Map<String, List<Client>> getClientsByCity) {
        this.getClientsByCity = getClientsByCity;
    }
}
