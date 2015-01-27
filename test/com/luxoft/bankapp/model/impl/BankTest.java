package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.exceptions.ClientExistsException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by SCJP on 26.01.15.
 */
public class BankTest {

    private Bank bank;

    @Before
    public void init(){
        bank = new Bank("test bank");

        Client client = new Client();
        client.setName("mark");
        client.setGender(Gender.MALE);
        client.setOverdraft(10);
        client.setPhone("888-8888888");
        client.setCity("moscow");
        client.setEmail("mark@mmm.ru");

        Set<Account> accounts = new HashSet<>();
        Account account = new CheckingAccount(50, 10);
        accounts.add(account);
        client.setActiveAccount(account);
        accounts.add(new SavingAccount(100));
        client.setAccounts(accounts);

        try {
            bank.addClient(client);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddClient() throws Exception {
        Client client = new Client("dnepr");
        client.setName("harry");
        client.setGender(Gender.MALE);
        Account account = new SavingAccount(10);
        client.addAccount(account);
        client.setActiveAccount(account);
        bank.addClient(client);
        assertEquals(2, bank.getClients().size());
    }

    @Test
    public void testRemoveClient() throws Exception {
        Client client = new Client("dnepr");
        client.setName("harry");
        client.setGender(Gender.MALE);
        Account account = new SavingAccount(10);
        client.addAccount(account);
        client.setActiveAccount(account);
        bank.addClient(client);
        Client client1 = bank.findClient("mark");
        bank.removeClient(client1);
        assertEquals(1, bank.getClients().size());
    }
}
