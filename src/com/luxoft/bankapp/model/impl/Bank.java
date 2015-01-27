package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.ClientRegistrationListener;
import com.luxoft.bankapp.model.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;

import java.text.SimpleDateFormat;
import java.util.*;

public class Bank {
    private long id;
    private String name;
    private Set<Client> clients = new HashSet<>();
    private List<ClientRegistrationListener> listeners = new ArrayList<>();
    private Map<String, Client> clientsMapByName = new HashMap<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public static class PrintClientListener implements ClientRegistrationListener {

        public void onClientAdded(Client c) {
            System.out.println("client added\n=========================================");
            System.out.println(c);
            System.out.println("=========================================");
        }
    }

    public static class EmailNotificationListener implements ClientRegistrationListener {

        public void onClientAdded(Client c) {
            System.out.println("Notification email for client " + c.getGender().getSalutation() + " " + c.getName() + " to be sent\n");
        }
    }

    public Bank() {
        this.listeners.add(new ClientRegistrationListener() {
            
            public void onClientAdded(Client c) {
                clientsMapByName.put(c.getName(), c);
            }
        });
    }

    public Bank(String name, List<ClientRegistrationListener> listeners) {
        this();
        this.name = name;
        this.listeners = listeners;
        listeners.add(new ClientRegistrationListener() {
            
            public void onClientAdded(Client c) {
                Date date = new Date();
                String stringDate = new SimpleDateFormat("h:mm a").format(date);
                System.out.println("" + c + " " + stringDate + "\n");
            }
        });
    }

    public Bank(String name) {
        this();
        this.name = name;
        listeners.add(new ClientRegistrationListener() {
            
            public void onClientAdded(Client c) {
                Date date = new Date();
                String stringDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
                System.out.println("" + c + " " + stringDate);
            }
        });
    }

    public List<ClientRegistrationListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<ClientRegistrationListener> listeners) {
        this.listeners = listeners;
    }

    public Set<Client> getClients() {
        return clients;
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

    public Client findClient(String name) throws ClientNotExistsException {
        for (Client client : clients) {
            if (client.getName().equals(name)) return client;
        }
        throw new ClientNotExistsException();
    }

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
    
    public void printReport() {
        System.out.println("Bank " + name + " has clients:");
        for (Client client : clients) {
            client.printReport();
            System.out.println("\n");
        }
    }
    
    public void addClient(Client client) throws ClientExistsException {
        if (hasClient(client)) throw new ClientExistsException();
        clients.add(client);
        for (ClientRegistrationListener listener : getListeners()) {
            listener.onClientAdded(client);
        }
    }

    private boolean hasClient(Client client) {
        boolean hasClient = false;
        for (Client c : clients) {
            if (c.equals(client)) {
                hasClient = true;
            }
        }
        return hasClient;
    }
    
    public void removeClient(Client client) {
        clients.remove(client);
        clientsMapByName.remove(client.getName());
    }

    public String toString() {
        return ("Bank " + name);
    }
}
