package com.luxoft.bankapp.commander;

import com.luxoft.bankapp.application.Io;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.BankException;

import java.io.IOException;

public interface Command {

    void execute(Io io);

    String printCommandInfo();
}
