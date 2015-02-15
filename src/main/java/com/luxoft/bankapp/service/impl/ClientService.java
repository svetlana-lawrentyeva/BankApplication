package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.dao.ClientDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;

public class ClientService {

    private ClientDao clientDao;

    /**
     * Save client to database
     * @param client client that will be saved
     */
    public Client save(Client client) throws DaoException {
        return clientDao.save(client);
    }

    /**
     * Remove client from database
     * @param client client that will be removed
     */
    public void remove(Client client) throws DaoException {
        clientDao.remove(client);
    }

    /**
     * Remove all clients from the bank in database
     * @param bank bank clients of which will be removed
     */
    public void removeAllByBank(Bank bank) throws DaoException {
        clientDao.removeAllByBank(bank);
    }

    /**
     * Get client by name in the bank from database
     * @param bank bank to find client in
     * @param name name of the client to find
     */
    public Client getByName(Bank bank, String name) throws ClientNotExistsException, DaoException {
        Client client = clientDao.getByName(bank, name);
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
        return clientDao.getById(clientId);
    }

    /**
     * Get all clients of the bank from database
     * @param bank bank that contents clients
     */
    public List<Client>getAllByBank(Bank bank) throws DaoException {
        return clientDao.getAllClients(bank);
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

    /**
     * Get all clients of the bank from database by name and city
     * @param bank bank that contents clients
     * @param name name of client to find
     * @param city city of client to find
     */
    public List<Client>getClientsByNameAndCity(Bank bank, String name, String city) throws DaoException, SQLException {
        return clientDao.findClientsByNameAndCity(bank, name, city);
    }


}
