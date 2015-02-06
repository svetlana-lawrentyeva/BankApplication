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

    private DaoFactory (){}

    /**
     * Get AccountDao
     */
    public static AccountDao getAccountDao(){
        return AccountDaoImpl.getInstance();
    }

    /**
     * Get ClientDao
     */
    public static ClientDao getClientDao(){
       return ClientDaoImpl.getInstance();
    }

    /**
     * Get BankDao
     */
    public static BankDao getBankDao(){
       return BankDaoImpl.getInstance();
    }
}
