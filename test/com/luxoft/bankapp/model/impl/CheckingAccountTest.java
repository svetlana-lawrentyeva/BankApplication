package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by SCJP on 26.01.15.
 */
public class CheckingAccountTest {

    private CheckingAccount account;


    @Test
    public void testBalanceInConstructor(){
        account = new CheckingAccount(0.5f);
        assertEquals(0.5f, account.getBalance(), 0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testSetNegativeBalanceInConstructor() {
        account = new CheckingAccount(-1.0f);
    }

    @Test
    public void testDeposit() {
        account = new CheckingAccount(0.5f);
        assertEquals(0.5f, account.getBalance(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositNegative() throws Exception {
        account = new CheckingAccount(0.5f);
        account.deposit(-100);
    }

    @Test
    public void testWithdraw() throws Exception {
        account = new CheckingAccount(100.5f);
        account.withdraw(100);
        assertEquals(0.5f, account.getBalance(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNegative() throws Exception {
        account = new CheckingAccount(100.5f);
        account.withdraw(-100);
    }

    @Test (expected = NotEnoughFundsException.class)
    public void testWithdrawTooMuch() throws Exception {
        account = new CheckingAccount(0.5f);
        account.withdraw(100);
    }

    @Test
    public void testGetAvailableMoney() throws Exception {
        account = new CheckingAccount(0.5f, 10.0f);
        assertEquals(10.5f, account.getAvailableMoney(), 0);

    }

    @Test
    public void testParseFeed() throws Exception {
        account = new CheckingAccount(0.5f);
        Map<String, String> feed = new HashMap<>();
        feed.put("accounttype", "c");
        feed.put("balance", "100");
        feed.put("overdraft", "50");
        feed.put("name", "John");
        feed.put("gender", "m");
        feed.put("city", "moscow");
        account.parseFeed(feed);
        assertEquals(100.0f, account.getBalance(), 0);
        assertEquals(50.0f, account.getOverdraft(), 0);
    }

    @Test(expected = NumberFormatException.class)
    public void testParseFeedWrongBalance() throws Exception {
        account = new CheckingAccount(0.5f);
        Map<String, String> feed = new HashMap<>();
        feed.put("accounttype", "c");
        feed.put("balance", "balance");
        feed.put("overdraft", "50");
        feed.put("name", "John");
        feed.put("gender", "m");
        feed.put("city", "moscow");
        account.parseFeed(feed);
    }

    @Test(expected = NumberFormatException.class)
    public void testParseFeedWrongOverdraft() throws Exception {
        account = new CheckingAccount(0.5f);
        Map<String, String> feed = new HashMap<>();
        feed.put("accounttype", "c");
        feed.put("balance", "100");
        feed.put("overdraft", "overdraft");
        feed.put("name", "John");
        feed.put("gender", "m");
        feed.put("city", "moscow");
        account.parseFeed(feed);
    }

    @Test
    public void testToString() throws Exception {
        account = new CheckingAccount(0.5f, 0.8f);
        String expectedReport = "Checking account " + account.getId() + " with balance: 0.5, overdraft: 0.8";
        assertTrue(account.toString().equals(expectedReport));
    }
}
