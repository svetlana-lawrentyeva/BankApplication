package com.luxoft.bankapp.application.threading;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;
import com.luxoft.bankapp.service.impl.ServiceFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by SCJP on 06.02.15.
 */
public class BankServerThreadedTest {
    private static Bank bank;
    private static Client client;

    @BeforeClass
    public static void createMockClient(){
        bank = ServiceFactory.getBankService().getByName("My bank");
        client = createClient("Ivan Ivanov", Gender.MALE, "Moscow", "123-4567890", "ivanov@gmail.com", 5000);
        Account account = createAccount(client, 50000, AccountType.CHECKING_ACCOUNT);
        client.setActiveAccount(account);
        try {
            ServiceFactory.getClientService().save(client);
        } catch (DaoException e) {
            System.out.println("error class: BankServerThreadedTest, method: createMockClient() - " + e.getMessage());
        }
    }

    @AfterClass
    public static void removeClient(){
        try {
            ServiceFactory.getClientService().remove(client);
        } catch (DaoException e) {
            System.out.println("error class: BankServerThreadedTest, method: removeClient() - " + e.getMessage());
        }
    }

    private static Client createClient(String name, Gender gender, String city, String phone, String email, float overdraft) {
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

    private static Account createAccount(Client client, float balance, AccountType type) {
        Account account;
        if (type == AccountType.CHECKING_ACCOUNT) {
            account = new CheckingAccount();
        } else {
            account = new SavingAccount();
        }
        account.setBalance(balance);
        account.setClient(client);
        client.addAccount(account);
        return account;
    }

    @Test
    public void clientWithdrawTest(){
        for(int i = 0;i<1000;++i){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("error class: BankServerThreadedTest, method: clientWithdrawTest() - "+e.getMessage());
            }
            System.out.println(i);
            new Thread(new BankClientMock(client, i)).start();
        }
    }

}
