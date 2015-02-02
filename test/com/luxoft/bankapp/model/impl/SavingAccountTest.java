package com.luxoft.bankapp.model.impl;


import static org.junit.Assert.*;

import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SCJP on 26.01.15.
 */
public class SavingAccountTest {

    private SavingAccount account;

    @Test
    public void testBalanceInConstructor(){
        account = new SavingAccount();
        account.setBalance(0.5f);
        assertEquals(0.5f, account.getBalance(), 0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testSetNegativeBalanceInConstructor() {
        account = new SavingAccount();
        account.setBalance(-1.0f);
    }

    @Test
    public void testDeposit() throws Exception {
        account = new SavingAccount();
        account.setBalance(0.5f);
        account.deposit(100);
        float f = account.getBalance();
        assertEquals(100.5f, account.getBalance(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositNegative() throws Exception {
        account = new SavingAccount();
        account.setBalance(0.5f);
        account.deposit(-100);
    }

    @Test
    public void testWithdraw() throws Exception {
        account = new SavingAccount();
        account.setBalance(100.5f);
        account.withdraw(100);
        assertEquals(0.5f, account.getBalance(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNegative() throws Exception {
        account = new SavingAccount();
        account.setBalance(100.5f);
        account.withdraw(-100);
    }

    @Test (expected = NotEnoughFundsException.class)
    public void testWithdrawTooMuch() throws Exception {
        account = new SavingAccount();
        account.setBalance(0.5f);
        account.withdraw(100);
    }

    @Test
    public void testGetAvailableMoney() throws Exception {
        account = new SavingAccount();
        account.setBalance(0.5f);
        assertEquals(0.5f, account.getAvailableMoney(), 0);
    }

    @Test
    public void testParseFeed() throws Exception {
        account = new SavingAccount();
        account.setBalance(0.5f);
        Map<String, String> feed = new HashMap<>();
        feed.put("accounttype", "s");
        feed.put("balance", "100");
        feed.put("overdraft", "0");
        feed.put("name", "John");
        feed.put("gender", "m");
        feed.put("city", "moscow");
        account.parseFeed(feed);
        assertEquals(100.0f, account.getBalance(), 0);
    }

    @Test(expected = NumberFormatException.class)
    public void testParseFeedWrongBalance() throws Exception {
        account = new SavingAccount();
        account.setBalance(0.5f);
        Map<String, String> feed = new HashMap<>();
        feed.put("accounttype", "s");
        feed.put("balance", "balance");
        feed.put("overdraft", "0");
        feed.put("name", "John");
        feed.put("gender", "m");
        feed.put("city", "moscow");
        account.parseFeed(feed);
    }
}
