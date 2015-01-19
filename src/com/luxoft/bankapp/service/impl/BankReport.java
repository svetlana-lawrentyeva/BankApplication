package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Report;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.ClientComparator;

import java.util.*;

/**
 * Created by SCJP on 19.01.15.
 */
public class BankReport {
    public int getNumberOfClients(Bank bank){
        int clientsNumber = bank.getClients().size();
        System.out.println("bank "+bank.getName()+" has "+clientsNumber+" clients");
        return clientsNumber;
    }

    public int getAccountsNumber(Bank bank){
        int accountsNumber = 0;
        for(Client client:bank.getClients()){
            accountsNumber = client.getAccounts().size();
        }
        System.out.println("bank "+bank.getName()+" has "+accountsNumber+" accounts");
        return accountsNumber;
    }

    public Set<Client> getClientsSorted(Bank bank){
        Set<Client>sortedClients = new TreeSet<Client>(new ClientComparator());
        for(Client client:sortedClients){
            System.out.println(client);
        }
        return sortedClients;
    }

    public float getBankCreditSum(Bank bank){
        float bankCreditSum = 0;
        for(Client client:bank.getClients()){
            for(Account account:client.getAccounts()){
                if(account.getClass().equals(CheckingAccount.class)){
                    if(account.getBalance()<0) bankCreditSum+=account.getBalance();
                }
            }
        }
        bankCreditSum *= -1;
        System.out.println("bank "+bank.getName()+" has credit "+bankCreditSum);
        return bankCreditSum;
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank){
        Map<String, List<Client>> resultMap = new TreeMap<String, List<Client>>();
        for(Client client:bank.getClients()){
            if(resultMap.containsKey(client.getCity()))
                resultMap.get(client.getCity()).add(client);
            else{
                List clients = new ArrayList<Client>();
                clients.add(client);
                resultMap.put(client.getCity(), clients);
            }
        }
        for(String city:resultMap.keySet()){
            List<Client> clientsByCity = resultMap.get(city);
            for(Client client:clientsByCity){
                System.out.println(city+"::"+client);
            }
        }
        return resultMap;
    }
}
