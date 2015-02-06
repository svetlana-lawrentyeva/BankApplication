package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.dao.impl.DaoFactory;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;
import com.luxoft.bankapp.service.TestService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by SCJP on 03.02.15.
 */
public class BankServiceTest {

    private Bank bank;
    private static final String BANK_NAME = "test bank";

    @Before
    public void initBank() {
        try {
            DaoFactory.getBankDao().delete(DaoFactory.getBankDao().getBankByName(BANK_NAME));
        } catch (DaoException e) {
            System.out.println(e.getMessage());;
        }
        bank = new Bank();
        bank.setName(BANK_NAME);

        Client client1 = createClient("harry", "dnepr", Gender.MALE, "111-1111111", "qqq@qq.qq", 1000);
        addAccount(AccountType.SAVING_ACCOUNT, 1200, client1);
        addAccount(AccountType.CHECKING_ACCOUNT, 2000, client1);
        bank.addClient(client1);

        Client client2 = createClient("mary", "moscow", Gender.FEMALE, "222-2222222", "www@ww.ww", 1500);
        addAccount(AccountType.SAVING_ACCOUNT, 2300, client2);
        addAccount(AccountType.CHECKING_ACCOUNT, 3100, client2);
        bank.addClient(client2);
    }



    private Client createClient(String name, String city, Gender gender, String phone, String email, float overdraft){
        Client client = new Client();
        client.setName(name);
        client.setGender(gender);
        client.setCity(city);
        client.setPhone(phone);
        client.setEmail(email);
        client.setOverdraft(overdraft);
        return client;
    }

    private void addAccount(AccountType accountType, float balance, Client client){
        Account account;
        switch (accountType){
            case SAVING_ACCOUNT:
                account = new SavingAccount();
                break;
            case CHECKING_ACCOUNT:
                account = new CheckingAccount();
                break;
            default:
                account = new SavingAccount();
        }
        account.setBalance(balance);
        client.addAccount(account);
    }

    @After
    public void drop(){
        try {
            DaoFactory.getBankDao().delete(bank);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetByName() throws Exception {
        ServiceFactory.getBankService().save(bank);
        Bank bank2 = ServiceFactory.getBankService().getByName(BANK_NAME);
        assertTrue(TestService.isEquals(bank, bank2));
    }

    @Test
    public void testGetById() throws Exception {
        ServiceFactory.getBankService().save(bank);
        long id = bank.getId();
        Bank bank2 = ServiceFactory.getBankService().getById(id);
        assertTrue(TestService.isEquals(bank, bank2));

    }

    @Test
    public void testGetAccountsNumber() throws Exception {
        ServiceFactory.getBankService().save(bank);
        int accountsNumber = ServiceFactory.getBankService().getAccountsNumber(bank);
        assertEquals(4, accountsNumber);
    }

    @Test
    public void testGetBankCreditSum() throws Exception {
        Set<Client>clients = bank.getClients();
        Set<Account>accounts = null;
        for(Client client:clients){
            accounts = client.getAccounts();
        for(Account account:accounts){
            if(account instanceof CheckingAccount){
                float balance = account.getBalance();
                float x = balance+10;
                account.withdraw(x);
            }
        }
    }
        ServiceFactory.getBankService().save(bank);
        float creditSum = ServiceFactory.getBankService().getBankCreditSum(bank);
        assertEquals(-20, creditSum, 0);
    }
}
