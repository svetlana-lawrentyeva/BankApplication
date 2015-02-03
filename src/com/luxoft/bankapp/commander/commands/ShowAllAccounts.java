package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;
import java.util.List;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 2/1/15
 * Time: 2:05 PM
 */
public class ShowAllAccounts extends AbstractCommand {

    public ShowAllAccounts(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(InputStream is, OutputStream os) throws DaoException, IOException, ClientNotExistsException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(is);
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(is, os);
            }
            List<Account> accounts = ServiceFactory.getAccountService().getAllByClient(getCommander().getCurrentClient());
            for(Account account:accounts){
                out.writeObject(account+" "+account.getBalance()+"\n");
            }
        out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override public String printCommandInfo() {
        return "show all accounts";
    }
}
