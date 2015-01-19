package com.luxoft.bankapp;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.model.impl.*;
import com.luxoft.bankapp.service.impl.BankService;

import java.util.ArrayList;

/**
 * Created by SCJP on 14.01.15.
 */
public class BankApplication {
    private Bank bank;

    private BankService bankService = new BankService();

    public static void main(String[] args) {
        BankApplication bankApplication = new BankApplication();
        bankApplication.initialize();
        bankApplication.printBankReport();
        bankApplication.modifyBank();
        bankApplication.printBankReport();
    }
    public void initialize(){
        BankImpl.PrintClientListener printClientListener = new BankImpl.PrintClientListener();
        BankImpl.EmailNotificationListener emailNotificationListener = new BankImpl.EmailNotificationListener();
        ArrayList<ClientRegistrationListener>listeners = new ArrayList<ClientRegistrationListener>();

        listeners.add(printClientListener);
        listeners.add(emailNotificationListener);

        //bank = new BankImpl("myBank",listeners);
        bank.setListeners(listeners);

        Client client1 = new ClientImpl("London");
        client1.setName("Peter Johnson");
        client1.setGender(Gender.MALE);
        client1.createAccount(AccountType.CHECKING_ACCOUNT, 200);
        client1.createAccount(AccountType.SAVING_ACCOUNT,100);
        Account account1 = new CheckingAccount(800, 300);
        bankService.addAccount(client1, account1);
        bankService.setActiveAccount(client1, account1);
        try {
            bankService.addClient(bank,client1);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }

        Client client2 = new ClientImpl("Moscow");
        client2.setName("Mike Greg");
        client2.setGender(Gender.MALE);
        client2.createAccount(AccountType.CHECKING_ACCOUNT,500);
        Account account2 = new CheckingAccount(800, 300);
        Account account3 = new SavingAccount(1500);
        bankService.addAccount(client2,account2);
        bankService.addAccount(client2,account3);
        bankService.setActiveAccount(client2, account2);
        try {
            bankService.addClient(bank,client2);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }

        Client client20 = new ClientImpl("Moscow");
        client20.setName("Mike Greg");
        client20.setGender(Gender.MALE);
        client20.createAccount(AccountType.CHECKING_ACCOUNT,500);
        Account account20 = new CheckingAccount(800, 300);
        Account account30 = new SavingAccount(1500);
        bankService.addAccount(client20,account20);
        bankService.addAccount(client20,account30);
        bankService.setActiveAccount(client20, account20);
        try {
            bankService.addClient(bank,client20);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("--------------------------"+client2.equals(client20));

        Client client3 = new ClientImpl("Kiev");
        client3.setName("Tom Jackson");
        client3.setGender(Gender.MALE);
        client3.createAccount(AccountType.CHECKING_ACCOUNT);
        client3.createAccount(AccountType.SAVING_ACCOUNT,100);
        Account account4 = new SavingAccount(1000);
        bankService.addAccount(client3, account4);
        bankService.setActiveAccount(client3, account4);
        try {
            bankService.addClient(bank,client3);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }

        Client client4 = new ClientImpl("Paris");
        client4.setName("Lion Queen");
        client4.setGender(Gender.FEMALE);
        client4.createAccount(AccountType.CHECKING_ACCOUNT);
        client4.createAccount(AccountType.SAVING_ACCOUNT, 300);
        client4.createAccount(AccountType.SAVING_ACCOUNT, 250);
        Account account5 = new CheckingAccount(750, 200);
        Account account6 = new CheckingAccount(1800, 1000);
        Account account7 = new SavingAccount(2500);
        bankService.addAccount(client4, account5);
        bankService.addAccount(client4, account6);
        bankService.addAccount(client4, account7);
        bankService.setActiveAccount(client4, account6);
        try {
            bankService.addClient(bank, client4);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }
        try {
            bankService.withdraw(client1, 900);
        } catch (BankException e) {
            e.printStackTrace();
        }
    }

    public void modifyBank(){
        Client client1 = bankService.getClient(bank, "Peter Johnson");
        Account account1 = client1.getActiveAccount();
        account1.deposit(1000);

        Client client2 = bankService.getClient(bank, "Mike Greg");
        Account account2 = client2.getActiveAccount();
        try {
            client2.withdraw(7000, account2);
        } catch (BankException e) {
            System.out.println("################## " + e.getMessage());
        }

        Client client3 = bankService.getClient(bank, "Peter Johnson");
        bankService.removeClient(bank,client3);
    }

    public void printBankReport(){
        bank.printReport();
    }

    public void initialize(Bank currentBank) {
        this.bank = currentBank;
        initialize();
    }
}
