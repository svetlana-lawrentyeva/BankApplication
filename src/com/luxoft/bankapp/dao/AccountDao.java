package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Client;

import java.util.List;


public interface AccountDao {
    /**
     * Save Account in database
     * @param account
     */
    public void save(Account account, Client client) throws DaoException;

    /**
     * Remove all accounts of client
     * @param id Id of the client
     */
    public void removeByClientId(long id) throws DaoException;

    /**
     * Get all accounts of the client
     * @param id Id of the client
     */
    public List<Account> getClientAccounts(long id) throws DaoException;

    public Account findAccountById(long id) throws DaoException;
}
