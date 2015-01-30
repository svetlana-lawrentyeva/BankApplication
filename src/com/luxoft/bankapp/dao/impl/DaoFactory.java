package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.AccountDao;
import com.luxoft.bankapp.dao.BankDao;
import com.luxoft.bankapp.dao.ClientDao;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 1/29/15
 * Time: 9:04 PM
 */
public class DaoFactory {
    private static AccountDao accountDao;
    private static ClientDao clientDao;
    private static BankDao bankDao;

    private DaoFactory (){}

    /**
     * Get AccountDao
     */
    public static AccountDao getAccountDao(){
        if(accountDao == null){
            accountDao = new AccountDaoImpl();
        }
        return accountDao;
    }

    /**
     * Get ClientDao
     */
    public static ClientDao getClientDao(){
        if(clientDao == null){
            clientDao = new ClientDaoImpl();
        }
        return clientDao;
    }

    /**
     * Get BankDao
     */
    public static BankDao getBankDao(){
        if(bankDao == null){
            bankDao = new BankDaoImpl();
        }
        return bankDao;
    }
}
