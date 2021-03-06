package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;

import java.util.List;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 1/27/15
 * Time: 7:25 PM
 */
public interface ClientDao {
    /**
     * Get client by name
     * @param bank bank to find client in
     * @param name name of client
     */
    Client getByName(Bank bank, String name) throws DaoException;

    /**
     * Get client by id
     * @param idClient id of client
     */
    Client getById(long idClient) throws DaoException;

    /**
     * Get all clients in bank
     * @param bank bank
     */
    List<Client> getAllClients(Bank bank) throws DaoException;

    /**
     * save the client
     * @param client client to be saved
     */
    Client save(Client client) throws DaoException;

    /**
     * remove the client
     * @param client client to be removed
     */
     void remove(Client client) throws DaoException;

        /**
         * remove all clients from the bank
         * @param bank bank to remove clients from
     */
    void removeAllByBank(Bank bank) throws DaoException;
}
