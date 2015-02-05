package com.luxoft.bankapp.commander;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.BankException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Command {

    void execute(ObjectInputStream is, ObjectOutputStream os) throws DaoException, BankException, IOException;

    String printCommandInfo();
}
