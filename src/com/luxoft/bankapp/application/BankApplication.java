package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.command.servicecommands.ServiceCommander;
import com.luxoft.bankapp.commander.command.servicecommands.AddClientCommand;
import com.luxoft.bankapp.commander.command.servicecommands.SetActiveAccountCommand;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.model.impl.*;

import java.util.ArrayList;

public class BankApplication {

    private Commander commander;
    private Bank bank;

    public static void main(String[] args) {

        BankApplication bankApplication = new BankApplication();
        bankApplication.commander = new ServiceCommander();
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

        Client client1 = new Client("London");
        client1.setName("Peter Johnson");
        client1.setGender(Gender.MALE);
        client1.setCity("moscow");
        client1.createAccount(AccountType.CHECKING_ACCOUNT, 200);
        client1.createAccount(AccountType.SAVING_ACCOUNT, 100);
        Account account1 = new CheckingAccount(800, 300);

        String feed = createFeed(client1, account1);
        new AddClientCommand(commander).execute(feed);
        new SetActiveAccountCommand(commander).execute(client1.getName() + "&" + account1.getId());

        Client client2 = new Client("Moscow");
        client2.setName("Mike Greg");
        client2.setGender(Gender.MALE);
        client2.setCity("london");
        client2.createAccount(AccountType.CHECKING_ACCOUNT, 500);
        Account account2 = new CheckingAccount(800, 300);
        Account account3 = new SavingAccount(1500);

        feed = createFeed(client2, account2);
        new AddClientCommand(commander).execute(feed);
        new SetActiveAccountCommand(commander).execute(client2.getName() + "&" + account2.getId());
        new AddClientCommand(commander).execute(feed);
        feed = createFeed(client2, account3);
        new AddClientCommand(commander).execute(feed);

        Client client20 = new Client("Moscow");
        client20.setName("Mike Greg");
        client20.setGender(Gender.MALE);
        client20.setCity("berlin");
        client20.createAccount(AccountType.CHECKING_ACCOUNT, 500);
        Account account20 = new CheckingAccount(800, 300);
        Account account30 = new SavingAccount(1500);

        feed = createFeed(client20, account20);
        new AddClientCommand(commander).execute(feed);
        new SetActiveAccountCommand(commander).execute(client20.getName() + "&" + account30.getId());
        new AddClientCommand(commander).execute(feed);
        feed = createFeed(client20, account30);
        new AddClientCommand(commander).execute(feed);


        Client client3 = new Client("Kiev");
        client3.setName("Tom Jackson");
        client3.setGender(Gender.MALE);
        client3.setCity("tokio");
        client3.createAccount(AccountType.CHECKING_ACCOUNT);
        client3.createAccount(AccountType.SAVING_ACCOUNT, 100);
        Account account4 = new SavingAccount(1000);

        feed = createFeed(client3, account4);
        new AddClientCommand(commander).execute(feed);
        new SetActiveAccountCommand(commander).execute(client3.getName() + "&" + account4.getId());
        new AddClientCommand(commander).execute(feed);
        feed = createFeed(client3, account4);
        new AddClientCommand(commander).execute(feed);


        Client client4 = new Client("Paris");
        client4.setName("Lion Queen");
        client4.setGender(Gender.FEMALE);
        client4.setCity("rome");
        client4.createAccount(AccountType.CHECKING_ACCOUNT);
        client4.createAccount(AccountType.SAVING_ACCOUNT, 300);
        client4.createAccount(AccountType.SAVING_ACCOUNT, 250);
        Account account5 = new CheckingAccount(750, 200);
        Account account6 = new CheckingAccount(1800, 1000);
        Account account7 = new SavingAccount(2500);

        feed = createFeed(client4, account5);
        new AddClientCommand(commander).execute(feed);
        new SetActiveAccountCommand(commander).execute(client4.getName() + "&" + account5.getId());
        new AddClientCommand(commander).execute(feed);
        feed = createFeed(client4, account5);
        new AddClientCommand(commander).execute(feed);
        feed = createFeed(client4, account6);
        new AddClientCommand(commander).execute(feed);
        feed = createFeed(client4, account7);
        new AddClientCommand(commander).execute(feed);

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
