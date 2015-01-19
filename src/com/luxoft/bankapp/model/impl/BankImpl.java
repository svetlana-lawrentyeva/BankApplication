package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.ClientRegistrationListener;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by SCJP on 14.01.15.
 */
public class BankImpl implements com.luxoft.bankapp.model.Bank {
    private String name;
    private Set<Client> clients = new HashSet<Client>();
    private List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
    private Map<String, Client> clientsMapByName;

    public BankImpl(){
        this.listeners.add(new ClientRegistrationListener() {
            @Override
            public void onClientAdded(Client c) {
                clientsMapByName.put(c.getName(), c);
            }
        });
    }
    public BankImpl(String name, List<ClientRegistrationListener>listeners){
        super();
        this.name = name;
       this.listeners = listeners;
        listeners.add(new ClientRegistrationListener() {
            @Override
            public void onClientAdded(Client c) {
                Date date = new Date();
                String stringDate = new SimpleDateFormat("h:mm a").format(date);
                System.out.println("" + c + " " + stringDate + "\n");
            }
        });
    }

    public BankImpl(String name){
        super();
        this.name = name;
        listeners.add(new ClientRegistrationListener() {
            @Override
            public void onClientAdded(Client c) {
                Date date = new Date();
                String stringDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
                System.out.println(""+c+" "+stringDate);
            }
        });
    }

    public List<ClientRegistrationListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<ClientRegistrationListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public Client findClient(String name) throws ClientNotExistsException {
        for(Client client:clients){
            if(client.getName().equals(name)) return client;
        }
        throw new ClientNotExistsException();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void parseFeed(Map<String, String> feed) {
        Client client = new ClientImpl();
        clients.add(client);
        client.parseFeed(feed);
    }

    public Map<String, Client> getClientsMapByName() {
        return clientsMapByName;
    }

    public void setClientsMapByName(Map<String, Client> clientsMapByName) {
        this.clientsMapByName = clientsMapByName;
    }

    public static class PrintClientListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client c) {
            System.out.println("client added\n=========================================");
            System.out.println(c);
            System.out.println("=========================================");
        }
    }

    public static class EmailNotificationListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client c) {
            System.out.println("Notification email for client "+c.getGender().getSalutation()+ " " + c.getName()+" to be sent\n");
        }
    }
    @Override
    public void printReport() {
        System.out.println("Bank "+name+" has clients:");
        for(Client client:clients){
            client.printReport();
            System.out.println("\n");
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bank "+name+" has clients:");
        for(Client client:clients){
            stringBuilder.append(client);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public Set<Client> getClients() {
        return clients;
    }

    @Override
    public void setClients(Set<Client> clients) {
        this.clients = clients;
        for(Client client:clients){
            for(ClientRegistrationListener listener: getListeners()){
                listener.onClientAdded(client);
            }
        }
    }

    @Override
    public void addClient(Client client) throws ClientExistsException {
        if(hasClient(client)) throw new ClientExistsException();
        clients.add(client);
        for(ClientRegistrationListener listener: getListeners()){
            listener.onClientAdded(client);
        }
    }

    private boolean hasClient(Client client){
        boolean hasClient = false;
        for(Client c:clients){
            if(c.equals(client))hasClient = true;
        }
        return hasClient;
    }
    @Override
    public void removeClient(Client client) {
        clients.remove(client);
    }
}
