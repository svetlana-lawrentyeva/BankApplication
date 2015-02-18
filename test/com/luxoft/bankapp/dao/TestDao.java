package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.luxoft.bankapp.service.TestService;
import static org.junit.Assert.*;

public class TestDao {
    private Bank bank;
    private static final String BANK_NAME = "test bank";

    @Before
    public void initBank() {
        try {
            getBankDao().delete(DaoFactory.getBankDao().getBankByName(BANK_NAME));
        } catch (DaoException e) {
            e.printStackTrace();
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

    @After
    public void drop(){
        try {
            DaoFactory.getBankDao().delete(bank);
        } catch (DaoException e) {
            e.printStackTrace();
        }
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


    @Test
    public void testInsert() {
        Bank bank2 = null;
        try {
            DaoFactory.getBankDao().save(bank);
            bank2 = DaoFactory.getBankDao().getBankByName(BANK_NAME);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertTrue(TestService.isEquals(bank, bank2));
    }
}
