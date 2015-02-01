package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.impl.Client;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BankInfo implements Serializable{
    private int clientsNumber;
    private int accountsNumber;
    private Set<Client> clientsSorted;
    private float bankCreditSum;
    private Map<String, List<Client>> clientsByCity;


    public int getClientsNumber() {
        return clientsNumber;
    }

    public void setClientsNumber(int clientsNumber) {
        this.clientsNumber = clientsNumber;
    }

    public int getAccountsNumber() {
        return accountsNumber;
    }

    public void setAccountsNumber(int accountsNumber) {
        this.accountsNumber = accountsNumber;
    }

    public Set<Client> getClientsSorted() {
        return clientsSorted;
    }

    public void setClientsSorted(Set<Client> clientsSorted) {
        this.clientsSorted = clientsSorted;
    }

    public float getBankCreditSum() {
        return bankCreditSum;
    }

    public void setBankCreditSum(float bankCreditSum) {
        this.bankCreditSum = bankCreditSum;
    }

    public Map<String, List<Client>> getClientsByCity() {
        return clientsByCity;
    }

    public void setClientsByCity(Map<String, List<Client>> clientsByCity) {
        this.clientsByCity = clientsByCity;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
            try{
                builder.append("number of clients: ").append(clientsNumber).append("\n");
                builder.append("number of accounts: ").append(accountsNumber).append("\n");
                builder.append("list of clients:\n");
                for(Client client:clientsSorted){
                    builder.append(client).append(" ").append(client.getBalance()).append("\n");
                }
                builder.append("credit sum: ").append(getBankCreditSum()).append("\n");
                builder.append("list of clients by city:\n");
                Set<String> cities = clientsByCity.keySet();
                for(String city:cities){
                    List<Client>clients = clientsByCity.get(city);
                    for(Client client:clients){
                        builder.append(city).append(":").append(client).append("\n");
                    }
                }
        } catch(Exception e){
                builder.append("error: ").append(e.getMessage());
            }
        return builder.toString();
    }
}
