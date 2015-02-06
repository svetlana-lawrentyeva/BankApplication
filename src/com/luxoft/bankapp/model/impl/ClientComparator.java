package com.luxoft.bankapp.model.impl;

import java.io.Serializable;
import java.util.Comparator;

public class ClientComparator implements Comparator<Client>, Serializable {
    @Override
    public int compare(Client o1, Client o2) {
        int result = 0;
        if (o1.getBalance() > o2.getBalance()) {
            result = 1;
        } else if (o1.getBalance() < o2.getBalance()) {
            result = -1;
        }
        return result;
    }
}
