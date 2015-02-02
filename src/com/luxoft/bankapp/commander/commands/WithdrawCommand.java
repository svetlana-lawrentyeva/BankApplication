package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class WithdrawCommand extends AbstractCommand implements Command {

    public WithdrawCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(InputStream is, OutputStream os) throws DaoException, BankException, IOException {
        PrintWriter out;
        BufferedReader in;
            out = new PrintWriter(new OutputStreamWriter(os));
            out.flush();
            in = new BufferedReader(new InputStreamReader(is));
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(is, os);
            }
            out.println("choose account number:");
        out.println("");
            out.flush();
            Command showAccounts = new ShowAllAccounts(getCommander());
            showAccounts.execute(is, os);
            long idAccount = Long.parseLong(in.readLine());
            getCommander().getCurrentClient().setActiveAccount(ServiceFactory.getAccountService().getById(idAccount));
            out.println("money to withdraw:");
        out.println("");
            out.flush();
            float x = Float.parseFloat(in.readLine());
            ServiceFactory.getAccountService().withdraw(getCommander().getCurrentClient().getActiveAccount(), x);
            out.println("Current client's active account " +
                    getCommander().getCurrentClient().getActiveAccount() + "balance: " +
                    getCommander().getCurrentClient().getActiveAccount().getBalance());
        out.println("");
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Withdraw";
    }
}
