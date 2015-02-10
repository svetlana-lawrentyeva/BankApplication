package com.luxoft.bankapp.application.threading;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BankServerThreadedTest {
    private static Bank bank;
    private static Client client;

    @Test
    public void clientWithdrawTest(){
        bank = ServiceFactory.getBankService().getByName("My bank");
        try {
            client = ServiceFactory.getClientService().getByName(bank, "Ivan Ivanov");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Account account = client.getAccounts().iterator().next();
        int rounds = 2;
        float startBalance = account.getBalance();
        client.setActiveAccount(account);
        ExecutorService pool = Executors.newFixedThreadPool(100);
        List<Future<Long>> futureList = new ArrayList<>();
        for(int i = 0;i<rounds;++i){
            BankClientMock mock = new BankClientMock(client, i);
            futureList.add(pool.submit(mock));
        }
        long sumResult = 0l;
        for(Future<Long> f:futureList){
            try {
                sumResult+=f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("average time is "+sumResult/rounds);

        pool.shutdown();
        try {
            account = ServiceFactory.getAccountService().getById(account.getId());
        } catch (DaoException e) {
            e.printStackTrace();
        }
        float finishBalance = account.getBalance();
        float difference = startBalance - finishBalance;
        System.out.println("differense: "+difference);
        assertEquals((int)difference, rounds);

    }
}
