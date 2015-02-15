package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.impl.ServiceFactory;
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
        bank = ServiceFactory.getBankService().getByName("My bank");

        Client client = new Client();
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
        account1.setBalance(100);
        accounts.add(account1);
        client.setAccounts(accounts);

            bank.addClient(client);
    }

    @Test
    public void testAddClient() throws Exception {
        Client client = new Client();
        client.setName("harry");
        client.setCity("dnepr");
        client.setGender(Gender.MALE);
        Account account = new SavingAccount();
        account.setBalance(10);
        client.addAccount(account);
        client.setActiveAccount(account);
        bank.addClient(client);
        assertEquals(2, bank.getClients().size());
    }

    @Test
    public void testRemoveClient() throws Exception {
        Client client = new Client();
        client.setName("name");
        client.setGender(Gender.FEMALE);
        client.setCity("city");
        client.setPhone("111-1111111");
        client.setEmail("email@email.com");
        client.setBank(bank);
        client.setOverdraft(123);
        ServiceFactory.getClientService().save(client);
        int startSize = ServiceFactory.getBankService().getClientsNumber(bank);
        Client client1 = ServiceFactory.getClientService().getByName(bank, "name");
        ServiceFactory.getClientService().remove(client1);
        int endSize = ServiceFactory.getBankService().getClientsNumber(bank);
        assertEquals(startSize - endSize, 1);
    }
}
