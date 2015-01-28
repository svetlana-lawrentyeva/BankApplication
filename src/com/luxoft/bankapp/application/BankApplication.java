package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.ClientDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.dao.impl.ClientDaoImpl;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.ClientRegistrationListener;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;

import java.util.ArrayList;

public class BankApplication {

    private Commander commander;
    private Bank bank;

    public static void main(String[] args) {

        BankApplication bankApplication = new BankApplication();
        bankApplication.commander = new Commander();
        bankApplication.bank = new Bank();
        bankApplication.commander.setCurrentBank(bankApplication.bank);

        bankApplication.initialize();
    }

    private String createFeed(Client client, Account account) {
        StringBuilder builder = new StringBuilder();
        builder.append(account.getClass().getName().toLowerCase().charAt(0)).append("&");
        builder.append(String.valueOf(account.getBalance())).append("&");
        builder.append(String.valueOf(client.getOverdraft())).append("&");
        builder.append(client.getName()).append("&");
        String gender = client.getGender().name();
        builder.append(gender.substring(gender.lastIndexOf(".") + 1).toLowerCase().charAt(0)).append("&");
        builder.append(client.getCity()).append("&");

        return builder.toString();
    }

    public void initialize() {
        Bank.PrintClientListener printClientListener = new Bank.PrintClientListener();
        Bank.EmailNotificationListener emailNotificationListener = new Bank.EmailNotificationListener();
        ArrayList<ClientRegistrationListener> listeners = new ArrayList<>();

        listeners.add(printClientListener);
        listeners.add(emailNotificationListener);

        bank.getListeners().addAll(listeners);

        ClientDao clientDao = new ClientDaoImpl();

        Client client1 = new Client();
        client1.setName("Peter Johnson");
        client1.setGender(Gender.MALE);
        client1.setCity("moscow");
        client1.setPhone("444-44444444");
        client1.setEmail("lll@yyy.kkk");
        client1.createAccount(AccountType.CHECKING_ACCOUNT, 200);
        client1.createAccount(AccountType.SAVING_ACCOUNT, 100);
        Account account1 = new CheckingAccount(800, 300);
        client1.addAccount(account1);
        client1.setActiveAccount(account1);



        Client client2 = new Client();
        client2.setName("Mike Greg");
        client2.setGender(Gender.MALE);
        client2.setCity("london");
        client2.setPhone("444-44444444");
        client2.setEmail("lll@yyy.kkk");
        client2.createAccount(AccountType.CHECKING_ACCOUNT, 500);
        Account account2 = new CheckingAccount(800, 300);
        Account account3 = new SavingAccount(1500);
        client2.addAccount(account2);
        client2.addAccount(account3);
        client2.setActiveAccount(account2);


        Client client20 = new Client("Moscow");
        client20.setName("Mike Greg");
        client20.setGender(Gender.MALE);
        client20.setCity("berlin");
        client20.setPhone("555-44444444");
        client20.setEmail("hhh@yyy.kkk");
        client20.createAccount(AccountType.CHECKING_ACCOUNT, 500);
        Account account20 = new CheckingAccount(800, 300);
        Account account30 = new SavingAccount(1500);
        client20.addAccount(account20);
        client20.addAccount(account30);
        client20.setActiveAccount(account20);

        Client client3 = new Client("Kiev");
        client3.setName("Tom Jackson");
        client3.setGender(Gender.MALE);
        client3.setCity("tokio");
        client3.setPhone("222-44444444");
        client3.setEmail("fffe@yyy.kkk");
        client3.createAccount(AccountType.CHECKING_ACCOUNT);
        client3.createAccount(AccountType.SAVING_ACCOUNT, 100);
        Account account4 = new SavingAccount(1000);
        client3.addAccount(account4);
        client3.setActiveAccount(account4);

        Client client4 = new Client("Paris");
        client4.setName("Lion Queen");
        client4.setGender(Gender.FEMALE);
        client4.setCity("rome");
        client4.setPhone("777-44444444");
        client4.setEmail("llliul@yyy.kkk");
        client4.createAccount(AccountType.CHECKING_ACCOUNT);
        client4.createAccount(AccountType.SAVING_ACCOUNT, 300);
        client4.createAccount(AccountType.SAVING_ACCOUNT, 250);
        Account account5 = new CheckingAccount(750, 200);
        Account account6 = new CheckingAccount(1800, 1000);
        Account account7 = new SavingAccount(2500);
        client4.addAccount(account5);
        client4.addAccount(account6);
        client4.addAccount(account7);
        client4.setActiveAccount(account5);

        try {
            clientDao.save(client1);
            clientDao.save(client2);
            clientDao.save(client20);
            clientDao.save(client3);
            clientDao.save(client4);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

    }

    public void printBankReport() {
        bank.printReport();
    }

    public void initialize(Bank currentBank, Commander commander) {
        this.bank = currentBank;
        this.commander = commander;
        initialize();
    }
}
