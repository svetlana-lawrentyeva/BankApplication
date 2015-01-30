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
    Account save(Account account) throws DaoException;

    /**
     * Remove all accounts of client
     * @param client client
     */
    void removeAllByClient(Client client) throws DaoException;

    /**
     * Remove the account
     * @param id Id of the account
     */
    void remove(Account account) throws DaoException;
    /**
     * Get all accounts of the client
     * @param client Id of the client
     */
    List<Account> getAllByClient(Client client) throws DaoException;

    /**
     * Get account by id
     * @param id Id of the account that should be found
     */
    Account getById(long id) throws DaoException;

    /**
     * Add an Account to the Client
     * @param client client
     * @param account account
     */
    void addAccountToClient(Client client, Account account) throws DaoException;

    /**
     * Get balance of the account
     * @param account account to get balance
     */
    float getBalance(Account account) throws DaoException;
}
