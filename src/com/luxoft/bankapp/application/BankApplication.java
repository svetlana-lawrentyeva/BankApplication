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

        try {
            ServiceFactory.getClientService().save(client1);
            ServiceFactory.getClientService().save(client2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void modifyBank(){
        try {
            Client client1 = ServiceFactory.getClientService().getByName(bank, "Peter Johnson");
            Client client2 = ServiceFactory.getClientService().getByName(bank, "Mike Greg");

            ServiceFactory.getAccountService().deposit(client1.getActiveAccount(), 111);
            ServiceFactory.getAccountService().withdraw(client2.getActiveAccount(), 99);

            ServiceFactory.getAccountService().transfer(client1.getActiveAccount(), client2.getActiveAccount(), 10000);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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

    private Account createAccount(Client client, float balance, AccountType type){
        Account account;
        if(type == AccountType.CHECKING_ACCOUNT){
            account = new CheckingAccount();
        } else {
            account  = new SavingAccount();
        }
        account.setBalance(balance);
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
