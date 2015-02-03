package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class WithdrawCommand extends AbstractCommand implements Command {

    public WithdrawCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(InputStream is, OutputStream os) throws DaoException, BankException, IOException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(is);
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(is, os);
            }
            out.writeObject("choose account number:");
            out.flush();
            Command showAccounts = new ShowAllAccounts(getCommander());
            showAccounts.execute(is, os);
            long idAccount = Long.parseLong((String) in.readObject());
            getCommander().getCurrentClient().setActiveAccount(ServiceFactory.getAccountService().getById(idAccount));
            out.writeObject("money to withdraw:");
            out.flush();
            float x = Float.parseFloat((String) in.readObject());
            ServiceFactory.getAccountService().withdraw(getCommander().getCurrentClient().getActiveAccount(), x);
            out.writeObject("Current client's active account " +
                    getCommander().getCurrentClient().getActiveAccount() + "balance: " +
                    getCommander().getCurrentClient().getActiveAccount().getBalance());
        out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String printCommandInfo() {
        return "Withdraw";
    }
}
