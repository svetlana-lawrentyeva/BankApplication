package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.dao.impl.DaoFactory;
import com.luxoft.bankapp.model.impl.Bank;

import java.util.HashSet;

public class BankService {
    /**
     * Get bank by name from darabase
     * @param name name to get the bank
     */
    public Bank getByName(String name){
        Bank bank = null;
        try {
            return DaoFactory.getBankDao().getBankByName(name);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return  bank;
    }

    /**
     * Get bank by id from database
     * @param idBank id of the bank to get from database
     */
    public Bank getById(long idBank){
        Bank bank = null;
        try {
            return DaoFactory.getBankDao().getBankById(idBank);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return  bank;
    }

    /**
     * Save bank to database
     * @param bank bank that will be saved
     */
    public Bank save(Bank bank){
        try {
            DaoFactory.getBankDao().save(bank);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return  bank;
    }

    public String getBankReport(Bank bank) throws DaoException {
        bank = getByName(bank.getName());
        return DaoFactory.getBankDao().getBankReport(bank);
    }
}
