package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.impl.*;
import com.luxoft.bankapp.remote.BankInfo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Service {

    public void addClient(Bank bank, Client client) throws ClientExistsException {
        bank.addClient(client);
    }

    public void removeClient(Bank bank, Client client) {
        bank.removeClient(client);
    }

    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    public void addAccount(Client client, float x, char type) {
        Account account;
        switch (type) {
            case 's':
                account = new SavingAccount(x);
                break;
            case 'c':
                account = new CheckingAccount(x);
                break;
            default:
                throw new IllegalArgumentException();
        }
        client.addAccount(account);
    }

    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    public void withdraw(Client client, float x) throws BankException {
        client.withdraw(x);
    }

    public Client findClient(Bank bank, String name) throws ClientNotExistsException {
        Client client = getClient(bank, name);
        if (client == null) {
            throw new ClientNotExistsException();
        }
        return client;
    }

    public Account getAccount(Client client, long idAccount) {
        Account account = null;
        for (Account a : client.getAccounts()) {
            if (a.getId() == idAccount) {
                account = a;
            }
        }
        if (account == null) {
            throw new IllegalArgumentException();
        }
        return account;
    }

    public String getActiveAccount(Client client) {
        return client.getActiveAccount().toString();
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

    public void saveClient(Client client, String param) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(param));
            oos.writeObject(client);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Client loadClient(String param) {
        Client client = new Client();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(param));
            client = (Client) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return client;
    }

    public void loadFeeds(Bank bank, String folder) throws ClientExistsException {
        File dir = new File(folder);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    loadFeeds(bank, f.getName());
                } else {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(f));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            parseFeed(bank, line);
                        }
                        br.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Client parseFeed(Bank bank, String feed) throws ClientExistsException {

        Map<String, String> map = new HashMap<String, String>();
        String[] parameters = feed.split(";");
        for (String str : parameters) {
            String[] mapData = str.split("=");
            map.put(mapData[0], mapData[1]);
        }
        Client client = bank.parseFeed(map);
        return client;
    }

    public BankInfo getBankInfo(Bank bank){
        BankInfo bankInfo = new BankInfo();
        BankReport bankReport = new BankReport();
        bankInfo.setClientsNumber(bankReport.getNumberOfClients(bank));
        bankInfo.setGetAccountsNumber(bankReport.getAccountsNumber(bank));
        bankInfo.setGetBankCreditSum(bankReport.getBankCreditSum(bank));
        bankInfo.setGetClientsByCity(bankReport.getClientsByCity(bank));
        bankInfo.setGetClientsSorted(bankReport.getClientsSorted(bank));

        return bankInfo;
    }
}
