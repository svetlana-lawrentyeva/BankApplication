package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
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
    Client findClientByName(Bank bank, String name) throws ClientNotExistsException, DaoException;

    Client findClientById(int id) throws ClientNotExistsException, DaoException;

    List<Client> getAllClients(Bank bank) throws DaoException;

    void save(Client client) throws DaoException;

    void remove(Client client) throws DaoException;
}
