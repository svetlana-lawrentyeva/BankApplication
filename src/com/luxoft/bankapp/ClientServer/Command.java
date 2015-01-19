package com.luxoft.bankapp.ClientServer;

import com.luxoft.bankapp.service.impl.BankService;

/**
 * Created by SCJP on 15.01.15.
 */
public interface Command {
    static BankService bankService = new BankService();

    String execute();
}
