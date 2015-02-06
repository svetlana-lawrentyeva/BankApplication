package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

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
    public void execute(Io io) {
        try {
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(io);
            }
            List<Account> accounts = ServiceFactory.getAccountService().getAllByClient(getCommander().getCurrentClient());
            StringBuilder builder = new StringBuilder();
            builder.append("choose account number:\n");
            for(Account account:accounts){
                builder.append(account).append(" ").append(account.getBalance()).append("\n");
            }
            io.write(builder.toString());
            String accNumber = io.read();
            getCommander().getCurrentClient().setActiveAccount(ServiceFactory.getAccountService().getById(Long.parseLong(accNumber)));
            io.write("active account is "+accNumber+"\nenter for continue ");
            io.read();
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override public String printCommandInfo() {
        return "Show all accounts";
    }
}
