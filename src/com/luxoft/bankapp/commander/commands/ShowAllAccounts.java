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
    public void execute(ObjectInputStream in, ObjectOutputStream out) throws DaoException, IOException, ClientNotExistsException {
        try {
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(in, out);
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
