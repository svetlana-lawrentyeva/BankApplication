package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.util.List;

public class BankApplication {

    private Bank bank = ServiceFactory.getBankService().getByName("My bank");

    public static void main(String[] args) {

        BankApplication bankApplication = new BankApplication();
        bankApplication.bank = ServiceFactory.getBankService().save(bankApplication.bank);

        bankApplication.clearBank();

        bankApplication.initialize();

        bankApplication.printBankReport();

        try {
            Client client1 = ServiceFactory.getClientService().getByName(bankApplication.bank, "Peter Johnson");
            Client client2 = ServiceFactory.getClientService().getByName(bankApplication.bank, "Mike Greg");

            ServiceFactory.getAccountService().deposit(client1.getActiveAccount(), 111);
            ServiceFactory.getAccountService().withdraw(client2.getActiveAccount(), 99);

            ServiceFactory.getAccountService().transfer(client1.getActiveAccount(), client2.getActiveAccount(), 10000);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ after modification ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        bankApplication.printBankReport();
    }

    public void clearBank(){
        try {
            List<Client>clients = ServiceFactory.getClientService().getAllByBank(bank);
            for(Client client:clients){
                ServiceFactory.getClientService().remove(client);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        bank.getListeners().add(new Bank.PrintClientListener());
        bank.getListeners().add(new Bank.EmailNotificationListener());

        Client client1 = createClient("Peter Johnson", Gender.MALE, "moscow", "444-4444444", "lll@yyy.kkk", 1000);
        Account account1 = createAccount(client1, 12000, AccountType.CHECKING_ACCOUNT);
        client1.addAccount(account1);
        client1.setActiveAccount(account1);

        Client client2 = createClient("Mike Greg", Gender.MALE, "london", "444-4444444", "lll@yyy.kkk", 520);
        Account account2 = createAccount(client2, 10000, AccountType.CHECKING_ACCOUNT);
        Account account3 = createAccount(client2, 11100, AccountType.SAVING_ACCOUNT);
        client2.addAccount(account2);
        client2.addAccount(account3);
        client2.setActiveAccount(account2);

//        Client client20 = createClient("Mike Tomson", Gender.MALE, "berlin", "555-4444444", "hhh@yyy.kkk", 100);
//        Account account20 = createAccount(client20, 100, AccountType.CHECKING_ACCOUNT);
//        Account account30 = createAccount(client20, 150, AccountType.SAVING_ACCOUNT);
//        client20.addAccount(account20);
//        client20.addAccount(account30);
//        client20.setActiveAccount(account20);
//
//        Client client3 = createClient("Tom Jackson", Gender.MALE, "tokyo", "222-4444444", "fffe@yyy.kkk", 700);
//        Account account4 = createAccount(client3, 1700, AccountType.SAVING_ACCOUNT);
//        client3.addAccount(account4);
//        client3.setActiveAccount(account4);
//
//        Client client4 = createClient("Lion Queen", Gender.FEMALE, "rome", "777-4444444", "llliul@yyy.kkk", 200);
//        Account account5 = createAccount(client3, 17000, AccountType.CHECKING_ACCOUNT);
//        Account account6 = createAccount(client3, 2000, AccountType.CHECKING_ACCOUNT);
//        Account account7 = createAccount(client3, 22020, AccountType.SAVING_ACCOUNT);
//        client4.addAccount(account5);
//        client4.addAccount(account6);
//        client4.addAccount(account7);
//        client4.setActiveAccount(account5);

        try {
            ServiceFactory.getClientService().save(client1);
            ServiceFactory.getClientService().save(client2);
//            ServiceFactory.getClientService().save(client20);
//            ServiceFactory.getClientService().save(client3);
//            ServiceFactory.getClientService().save(client4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private Client createClient(String name, Gender gender, String city, String phone, String email, float overdraft){
        Client client = new Client();
        client.setName(name);
        client.setGender(gender);
        client.setCity(city);
        client.setPhone(phone);
        client.setEmail(email);
        client.setBank(bank);
        client.setOverdraft(overdraft);

        bank.addClient(client);

        return client;
    }

    private Account createAccount(Client client, float balanse, AccountType type){
        Account account;
        if(type == AccountType.CHECKING_ACCOUNT){
            account = new CheckingAccount();
        } else {
            account  = new SavingAccount();
        }
        account.setBalance(balanse);
        account.setClient(client);
        client.addAccount(account);
        return account;
    }

    public void printBankReport() {
        try {
            System.out.println(ServiceFactory.getBankService().getBankReport(bank));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void initialize(Bank currentBank, Commander commander) {
        this.bank = currentBank;
        initialize();
    }
}
