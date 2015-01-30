package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.service.BankInfo;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 1/27/15
 * Time: 7:24 PM
 */
public interface BankDao {

    /**
     * Get bank by name
     * @param name Name of the bank
     */
    Bank getBankByName(String name) throws DaoException;

    /**
     * Get bank by id
     * @param bankId id of the bank
     */
    Bank getBankById(long bankId) throws DaoException;

    /**
     * Save bank
     * @param bank The bank to be saved
     */
    Bank save(Bank bank) throws DaoException;

    /**
     * Get Bank Info
     * @param bank The bank to get bank info
     */
    BankInfo getBankInfo(Bank bank);

    /**
     * Create Bank report
     * @param bank The bank to get bank report
     */
    String getBankReport(Bank bank) throws DaoException;
}
