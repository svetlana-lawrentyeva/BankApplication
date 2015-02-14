package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.dao.impl.DaoFactory;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ClientService {

    /**
     * Save client to database
     * @param client client that will be saved
     */
    public Client save(Client client) throws DaoException {
        return DaoFactory.getClientDao().save(client);
    }

    /**
     * Remove client from database
     * @param client client that will be removed
     */
    public void remove(Client client) throws DaoException {
        DaoFactory.getClientDao().remove(client);
    }

    /**
     * Remove all clients from the bank in database
     * @param bank bank clients of which will be removed
     */
    public void removeAllByBank(Bank bank) throws DaoException {
        DaoFactory.getClientDao().removeAllByBank(bank);
    }

    /**
     * Get client by name in the bank from database
     * @param bank bank to find client in
     * @param name name of the client to find
     */
    public Client getByName(Bank bank, String name) throws ClientNotExistsException, DaoException {
        Client client = DaoFactory.getClientDao().getByName(bank, name);
        if (client == null) {
            throw new ClientNotExistsException();
        }
        return client;
    }

    /**
     * Get client by id from database
     * @param clientId id of the client to find
     */
    public Client getById(long clientId) throws ClientNotExistsException, DaoException {
        return DaoFactory.getClientDao().getById(clientId);
    }

    /**
     * Get all clients of the bank from database
     * @param bank bank that contents clients
     */
    public List<Client>getAllByBank(Bank bank) throws DaoException {
        return DaoFactory.getClientDao().getAllClients(bank);
    }

    /**
     * Set client's active account
     * @param client client to set active account
     * @param account account that will be set as active
     */
    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    /**
     * Remove active account from the client
     * @param client client's active account will be null
     */
    public void removeActiveAccount(Client client) {
        client.setActiveAccount(null);
    }

    /**
     * Save the client on hard drive
     * @param client client to save
     * @path path to save the client
     */
    public void saveOnDisk(Client client, String path) throws BankException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(client);
            oos.close();
        } catch (Exception e) {
            throw new BankException(e.getMessage());
        }
    }

    /**
     * Load client from hard drive
     * @param path path to load client from
     */
    public Client loadFromDisk(String path) throws BankException {
        Client client = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            client = (Client) ois.readObject();
            ois.close();
        } catch (Exception e) {
            throw new BankException(e.getClass().getSimpleName() + " " + e.getMessage());
        }

        return client;
    }

}
