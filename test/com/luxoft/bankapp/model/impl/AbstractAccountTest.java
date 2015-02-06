package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Map;

/**
 * Created by SCJP on 26.01.15.
 */
public class AbstractAccountTest {

    private class TestingAccount extends AbstractAccount {

        @Override
        public void deposit(float x) {
            setBalance(getBalance()+x);
        }

        @Override
        public void withdraw(float x) throws NotEnoughFundsException {
            if (getBalance() < x) {
                throw new NotEnoughFundsException();
            }
            setBalance(getBalance()-x);
        }

        @Override
        public float getAvailableMoney() {
            return getBalance();
        }

        @Override
        public void parseFeed(Map<String, String> feed) {
            setBalance(Float.parseFloat(feed.get("balance")));
        }

        @Override
        public float getOverdraft() {
            return 0;
        }

        @Override
        public void setOverdraft(float overdraft) {

        }

        @Override
        public String printReport() {
            System.out.println(this);
            return  this.toString();
        }

        public String toString() {
            return "Abstract account " + getId() + " with balance: " + getBalance();
        }
    }

    private TestingAccount sut;

    @Test
    public void testSetBalanceInConstructor() {
        sut = new TestingAccount();
        sut.setBalance(1.0f);
        assertEquals(1.0f, sut.getBalance(), 0);
    }

    @Test
    public void testDecimalValue() throws Exception {
        sut = new TestingAccount();
        sut.setBalance(0.5f);
        assertEquals(1, sut.decimalValue(), 0);
    }
    @Test
    public void testEquals() throws Exception {
        sut = new TestingAccount();
        sut.setBalance(0.5f);
        TestingAccount sutTest = new TestingAccount();
        sutTest.setBalance(0.5f);
        sutTest.setId(sut.getId());
        boolean res = sut.equals(sutTest);
        assertEquals(sut, sutTest);
    }
}
