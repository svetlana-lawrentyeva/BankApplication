package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Client;

import java.util.Comparator;

/**
 * Created by SCJP on 19.01.15.
 */
public class ClientComparator implements Comparator<Client> {
    @Override
    public int compare(Client o1, Client o2) {
        int result = 0;
        if (o1.getActiveAccount().getBalance() > o2.getActiveAccount().getBalance())
            result = 1;
        else if (o1.getActiveAccount().getBalance() < o2.getActiveAccount().getBalance())
            result = -1;
        return result;
    }
}