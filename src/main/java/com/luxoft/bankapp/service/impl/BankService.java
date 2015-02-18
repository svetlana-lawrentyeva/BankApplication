package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.dao.BankDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.BankInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BankService {
    
    private BankDao bankDao;

    /**
     * Get bank by name from database
     * @param name name to get the bank
     */
    public Bank getByName(String name){
        Bank bank = null;
        try {
            return getBankDao().getBankByName(name);
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
            return getBankDao().getBankById(idBank);
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
            getBankDao().save(bank);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return  bank;
    }

    public String getBankReport(Bank bank) throws DaoException {
        bank = getByName(bank.getName());
        return getBankDao().getBankReport(bank);
    }

    public BankInfo getBankInfo(Bank bank){
        bank = getByName(bank.getName());
        return getBankDao().getBankInfo(bank);
    }

    public int getClientsNumber(Bank bank) throws DaoException {
        return getBankDao().getClientsNumber(bank);
    }

    public int getAccountsNumber(Bank bank) throws DaoException{
        return getBankDao().getAccountsNumber(bank);
    }

    public float getBankCreditSum(Bank bank) throws DaoException{
        return getBankDao().getBankCreditSum(bank);
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank){
        return getBankDao().getClientsByCity(bank);
    }

    public Set<Client> getClientsSorted(Bank bank) throws DaoException{
        return getBankDao().getClientsSorted(bank);
    }


    public BankDao getBankDao() {
        return bankDao;
    }

    public void setBankDao(BankDao bankDao) {
        this.bankDao = bankDao;
    }
}
