package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class AddAccountCommand extends AbstractCommand {

    public AddAccountCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(InputStream is, OutputStream os) throws IOException, ClientNotExistsException, DaoException {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(os));
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            out.println("type of account (c|s):");
        out.println("");
            out.flush();
            String typeAccount = in.readLine();
            out.println("start balance:");
        out.println("");
            out.flush();
            float startBalance = Float.parseFloat(in.readLine());
            Client client = null;
            while((client = getCommander().getCurrentClient()) == null){
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(is, os);
            }
            Account account;
            switch (typeAccount){
                case "c":
                    account = new CheckingAccount();
                    break;
                case "s":
                    account = new SavingAccount();
                    break;
                default:
                    throw new IllegalArgumentException("error: wrong balance type");
            }
            account.setBalance(startBalance);
            client.addAccount(account);
            ServiceFactory.getAccountService().save(account);
            out.println("success");
        out.println("");
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Add account";
    }
}
