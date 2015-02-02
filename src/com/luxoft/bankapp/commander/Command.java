package com.luxoft.bankapp.commander;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Command {

    void execute(InputStream is, OutputStream os) throws DaoException, BankException, IOException;

    String printCommandInfo();
}
