package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.ClientRegistrationListener;
import com.luxoft.bankapp.model.Report;
import com.luxoft.bankapp.model.exceptions.ClientExistsException;

import java.util.*;

public class Bank implements Report{

    private long id = -1;
    private String name;
    private Set<Client> clients = new HashSet<>();
    private List<ClientRegistrationListener> listeners = new ArrayList<>();
    private Map<String, Client> clientsMapByName = new HashMap<>();

    //-----------------------------------------------------------------------------

    public static class PrintClientListener implements ClientRegistrationListener {

        public void onClientAdded(Client c) {
            System.out.println(c.getClientSalutation() + " added");
        }
    }

    public static class EmailNotificationListener implements ClientRegistrationListener {

        public void onClientAdded(Client c) {
            System.out.println("Notification email for client " + c.getClientSalutation() + " to be sent");
            System.out.println("=========================================");
        }
    }

    //-----------------------------------------------------------------------------

    public Bank() {
        this.listeners.add(new ClientRegistrationListener() {

            public void onClientAdded(Client c) {
                clientsMapByName.put(c.getName(), c);
            }
        });
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ClientRegistrationListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<ClientRegistrationListener> listeners) {
        this.listeners = listeners;
    }

    public Set<Client> getClients(){
        return Collections.unmodifiableSet(clients);
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
        for (Client client : clients) {
            for (ClientRegistrationListener listener : getListeners()) {
                listener.onClientAdded(client);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Client> getClientsMapByName() {
        return clientsMapByName;
    }

    public void setClientsMapByName(Map<String, Client> clientsMapByName) {
        this.clientsMapByName = clientsMapByName;
    }

    public void addClient(Client client){
        clients.add(client);
        for(ClientRegistrationListener listener:listeners){
            listener.onClientAdded(client);
        }
    }

    //----------------------------------------------------------------------------

    private boolean hasClient(Client client) {
        boolean hasClient = false;
        for (Client c : clients) {
            if (c.equals(client)) {
                hasClient = true;
            }
        }
        return hasClient;
    }

    /**
     * Parse feed map to load data
     * @param feed map for parsing for get data
     */
    public Client parseFeed(Map<String, String> feed) throws ClientExistsException {
        Client client = new Client();
        client.parseFeed(feed);
        if(hasClient(client)){
            for (Client c : clients) {
                if (client.equals(c)) {
                    client = c;
                }
            }
        } else {
            clients.add(client);
            for (ClientRegistrationListener listener : getListeners()) {
                listener.onClientAdded(client);
            }
        }
        return client;
    }

    @Override
    public String printReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("Bank ").append(name).append("\n");
        builder.append("clients:");
        builder.append("\n------------------------------------");
        for (Client client : clients) {
            builder.append(client).append("\n------------------------------------");
        }
        System.out.println(builder);
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Bank ").append(name).append("\n");
        return builder.toString();
    }
}
