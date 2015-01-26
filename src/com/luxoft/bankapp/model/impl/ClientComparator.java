package com.luxoft.bankapp.model.impl;

import java.io.Serializable;
import java.util.Comparator;

public class ClientComparator implements Comparator<Client>, Serializable {
    @Override
    public int compare(Client o1, Client o2) {
        int result = 0;
        if (o1.getActiveAccount().getBalance() > o2.getActiveAccount().getBalance()) {
            result = 1;
        } else if (o1.getActiveAccount().getBalance() < o2.getActiveAccount().getBalance()) {
            result = -1;
        }
        return result;
    }
}
