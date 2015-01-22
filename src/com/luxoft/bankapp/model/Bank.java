package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.ClientExistsException;
import com.luxoft.bankapp.model.impl.ClientNotExistsException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Bank extends Report {
    @Override
    void printReport();

    Set<Client> getClients();

    void setClients(Set<Client> clients);

    void addClient(Client client) throws ClientExistsException;

    void removeClient(Client client);

    public List<ClientRegistrationListener> getListeners();

    public void setListeners(List<ClientRegistrationListener> listeners);

    public Client findClient(String name) throws ClientNotExistsException, ClientNotExistsException;

    public Map<String, Client> getClientsMapByName();

    public void setClientsMapByName(Map<String, Client> clientsMapByName);

    public String getName();

    public void setName(String name);

    Client parseFeed(Map<String, String> feed) throws ClientExistsException;
}
