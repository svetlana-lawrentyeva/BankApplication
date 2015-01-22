package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.impl.Client;

public interface ClientRegistrationListener {
    void onClientAdded(Client c);
}
