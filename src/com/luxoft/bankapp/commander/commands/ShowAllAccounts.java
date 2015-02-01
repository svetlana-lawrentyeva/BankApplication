package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Account;
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

    private BufferedReader in;
    private PrintWriter out;
    private InputStream is;
    private OutputStream os;

    public ShowAllAccounts(Commander commander, InputStream is, OutputStream os) {
        super(commander);
        this.is = is;
        this.os = os;
        in = new BufferedReader(new InputStreamReader(is));
        out = new PrintWriter(new OutputStreamWriter(os));
    }

    @Override public void execute() {
        try {
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander(), is, os);
                command.execute();
            }
            List<Account> accounts = ServiceFactory.getAccountService().getAllByClient(getCommander().getCurrentClient());
            for(Account account:accounts){
                out.println(account+" "+account.getBalance()+"\n");
            }
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.flush();
    }

    @Override public String printCommandInfo() {
        return "show all accounts";
    }
}
