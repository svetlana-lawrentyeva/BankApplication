package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.impl.Client;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 30.01.15
 * Time: 8:09
 */
public interface AccountRegistrationListener {
    void onAccountAdded(Client client, Account account);
}
