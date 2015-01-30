package com.luxoft.bankapp.model.impl;

import com.luxoft.bankapp.model.Account;

/**
 * Created with IntelliJ IDEA.
 * User: nau
 * Date: 30.01.15
 * Time: 8:09
 * To change this template use File | Settings | File Templates.
 */
public interface AccountRegistrationListener {
    void onAccountAdded(Client client, Account account);
}
