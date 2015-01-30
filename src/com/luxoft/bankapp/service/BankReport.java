package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.ClientComparator;

import java.io.Serializable;
import java.util.*;

public class BankReport implements Serializable{
    private StringBuilder report = new StringBuilder();
    private Bank bank;


    public String toString(){
        report = new StringBuilder();
        getNumberOfClients();
        getAccountsNumber();
        getClientsSorted();
        getBankCreditSum();
        getClientsByCity();

        return report.toString();
    }

    public int getNumberOfClients() {
        int clientsNumber = bank.getClients().size();
        String message = "bank " + bank.getName() + " has " + clientsNumber + " clients";
        report.append(message).append("\n");
        System.out.println(message);
        return clientsNumber;
    }

    public int getAccountsNumber() {
        int accountsNumber = 0;
        for (Client client : bank.getClients()) {
            accountsNumber += client.getAccounts().size();
        }
        String message = "bank " + bank.getName() + " has " + accountsNumber + " accounts";
        report.append(message).append("\n");
        System.out.println(message);
        return accountsNumber;
    }

    public Set<Client> getClientsSorted() {
        Comparator<Client> c = new ClientComparator();
        Set<Client> sortedClients = new TreeSet<>(c);
        sortedClients.addAll(bank.getClients());
        for (Client client : sortedClients) {
            report.append(client).append("\n");
            System.out.println(client);
        }
        return sortedClients;
    }

    public float getBankCreditSum() {
        float bankCreditSum = 0;
        for (Client client : bank.getClients()) {
            for (Account account : client.getAccounts()) {
                if (account.getClass().equals(CheckingAccount.class)) {
                    if (account.getBalance() < 0) {
                        bankCreditSum += account.getBalance();
                    }
                }
            }
        }
        bankCreditSum *= -1;
        String message = "bank " + bank.getName() + " has credit " + bankCreditSum;
        report.append(message).append("\n");
        System.out.println(message);
        return bankCreditSum;
    }

    public Map<String, List<Client>> getClientsByCity() {
        Map<String, List<Client>> resultMap = new TreeMap<String, List<Client>>();
        for (Client client : bank.getClients()) {
            if (resultMap.containsKey(client.getCity())) {
                resultMap.get(client.getCity()).add(client);
            } else {
                List clients = new ArrayList<Client>();
                clients.add(client);
                resultMap.put(client.getCity(), clients);
            }
        }
        for (String city : resultMap.keySet()) {
            List<Client> clientsByCity = resultMap.get(city);
            for (Client client : clientsByCity) {
                report.append(city + "::" + client).append("\n");
                System.out.println(city + "::" + client);
            }
        }
        return resultMap;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
