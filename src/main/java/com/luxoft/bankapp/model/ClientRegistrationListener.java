package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.impl.Client;

public interface ClientRegistrationListener {

    /**
     * On client added
     * @param c client
     */
    void onClientAdded(Client c);
}
