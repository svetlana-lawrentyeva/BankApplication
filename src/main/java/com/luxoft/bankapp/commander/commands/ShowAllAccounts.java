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

    @Override
    public void execute(Io io) {
        try {
            Client client = null;
            while ((client = getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand();
                command.execute(io);
            }
            List<Account> accounts = getServiceFactory().getAccountService().getAllByClient(getCurrentClient());
            StringBuilder builder = new StringBuilder();
            builder.append("choose account number:\n");
            for(Account account:accounts){
                builder.append(account).append(" ").append(account.getBalance()).append("\n");
            }
            io.write(builder.toString());
            String accNumber = io.read();
            getCurrentClient().setActiveAccount(getServiceFactory().getAccountService().getById(Long.parseLong(accNumber)));
            io.write("active account is "+accNumber+"\nenter for continue");
            io.read();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override public String printCommandInfo() {
        return "Show all accounts";
    }
}
