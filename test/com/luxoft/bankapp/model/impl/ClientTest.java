package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.impl.ServiceFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by SCJP on 26.01.15.
 */
public class ClientTest {

    private Client client;

    @Before
    public void init(){
        client = new Client();
        client.setName("mark");
        client.setGender(Gender.MALE);
        client.setOverdraft(10);
        client.setPhone("888-8888888");
        client.setCity("moscow");
        client.setEmail("mark@mmm.ru");

        Set<Account> accounts = new HashSet<>();
        Account account = new CheckingAccount();
        account.setBalance(50);
        accounts.add(account);
        client.setActiveAccount(account);
        Account account1 = new SavingAccount();
        account1.setBalance(600);
        accounts.add(account1);
        client.setAccounts(accounts);
    }

    @Test
    public void testDeposit() throws Exception {
        Account account = client.getActiveAccount();
        ServiceFactory.getAccountService().deposit(account, 20.0f);
        assertEquals(70.0f, account.getBalance(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositNegative() throws Exception {
        Account account = client.getActiveAccount();
        ServiceFactory.getAccountService().deposit(account, -20.0f);
    }

    @Test
    public void testWithdraw() throws Exception {
        Account account = client.getActiveAccount();
        ServiceFactory.getAccountService().withdraw(account, 20.0f);
        assertEquals(30.0f, account.getBalance(), 0);
    }

    @Test
    public void testWithdrawTakenOverdraft() throws Exception {
        Account account = client.getActiveAccount();
        ServiceFactory.getAccountService().withdraw(account,55.0f);
        assertEquals(-5.0f, account.getBalance(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNegative() throws Exception {
        Account account = client.getActiveAccount();
        ServiceFactory.getAccountService().withdraw(account, -20.0f);
    }

    @Test
    public void testCreateAccount() throws Exception {
        Account account = new SavingAccount();
        account.setBalance(9);
        client.addAccount(account);
        assertEquals(3, client.getAccounts().size());
    }

    @Test
    public void testToString() throws Exception {
        String expecktedString = "Mr. mark  50.0";
        assertTrue(client.toString().equals(expecktedString));
    }

    @Test
    public void testGetClientSalutation() throws Exception {
        String expectedString = "Mr. mark";
        assertTrue(client.getClientSalutation().equals(expectedString));
    }

    @Test
    public void testTransfer() throws Exception {
        Client client1 = new Client();
        client1.setName("elena");
        client1.setGender(Gender.FEMALE);
        client1.setOverdraft(50);
        client1.setPhone("999-9999999");
        client1.setCity("kharkov");
        client1.setEmail("elena@mmm.ru");

        Set<Account> accounts = new HashSet<>();
        Account account = new CheckingAccount();
        account.setBalance(20);
        accounts.add(account);
        client1.setActiveAccount(account);
        client1.setAccounts(accounts);

        ServiceFactory.getAccountService().transfer(client.getActiveAccount(), client1.getActiveAccount(), 20);

        assertEquals(30, client.getActiveAccount().getBalance(), 0);
        assertEquals(40, client1.getActiveAccount().getBalance(), 0);
    }

    @Test
    public void testTransferTakenOverdraft() throws Exception {
        Client client1 = new Client();
        client1.setName("elena");
        client1.setGender(Gender.FEMALE);
        client1.setOverdraft(50);
        client1.setPhone("999-9999999");
        client1.setCity("kharkov");
        client1.setEmail("elena@mmm.ru");

        Set<Account> accounts = new HashSet<>();
        Account account = new CheckingAccount();
        accounts.add(account);
        client1.setActiveAccount(account);
        client1.setAccounts(accounts);

        ServiceFactory.getAccountService().transfer(client.getActiveAccount(), client1.getActiveAccount(), 55);

        assertEquals(-5, client.getActiveAccount().getBalance(), 0);
        assertEquals(75, client1.getActiveAccount().getBalance(), 0);
    }

    @Test(expected = NotEnoughFundsException.class)
    public void testTransferNegative() throws Exception {
        Client client1 = new Client();
        client1.setName("elena");
        client1.setGender(Gender.FEMALE);
        client1.setOverdraft(50);
        client1.setPhone("999-9999999");
        client1.setCity("kharkov");
        client1.setEmail("elena@mmm.ru");

        Set<Account> accounts = new HashSet<>();
        Account account = new CheckingAccount();
        account.setBalance(20);
        accounts.add(account);
        client1.setActiveAccount(account);
        client1.setAccounts(accounts);

        ServiceFactory.getAccountService().transfer(client.getActiveAccount(), client1.getActiveAccount(), 65);

        assertEquals(20, client1.getActiveAccount().getBalance(), 0);
    }

    @Test
    public void testParseFeed() throws Exception {
        client = new Client();
        Map<String, String> feed = new HashMap<>();
        feed.put("accounttype", "s");
        feed.put("balance", "100");
        feed.put("overdraft", "0");
        feed.put("name", "John");
        feed.put("gender", "m");
        feed.put("city", "moscow");
        client.parseFeed(feed);
        assertTrue(client.getName().equals("John"));
        assertTrue(client.getCity().equals("moscow"));
        assertEquals(Gender.MALE, client.getGender());
        assertEquals(client.getActiveAccount().getClass(), SavingAccount.class);
        assertEquals(client.getActiveAccount().getBalance(), 100, 0);
    }
}
