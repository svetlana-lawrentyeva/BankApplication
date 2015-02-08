package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.util.List;

public class TransferCommand extends AbstractCommand implements Command {

    public TransferCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(Io io) {
        try {
        
            Client currentClient = null;
            while ((currentClient = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(io);
            }
            Account currentClientAccount = null;
            while ((currentClientAccount = getCommander().getCurrentClient().getActiveAccount()) == null) {
                ShowAllAccounts command = new ShowAllAccounts(getCommander());
                command.execute(io);
            }
            io.write("name of client to transfer:");
            Client client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), io.read());

            List<Account> accounts = ServiceFactory.getAccountService().getAllByClient(client);
            StringBuilder builder = new StringBuilder();
            builder.append("choose account number:\n");
            for (Account acc : accounts) {
                builder.append(acc).append(" ").append(acc.getBalance()).append("\n");
            }
            io.write(builder.toString());
            long idAccount = Long.parseLong((String) io.read());
            Account account = ServiceFactory.getAccountService().getById(idAccount);
            client.setActiveAccount(account);

            io.write("money to transfer:");
            
            float x = Float.parseFloat((String) io.read());
            ServiceFactory.getAccountService().transfer(currentClientAccount, account, x);
            builder = new StringBuilder();
            builder.append("Current client's account ").append(currentClientAccount).append(" ").append(currentClientAccount.getBalance());
            builder.append("\n").append("Client ").append(client.getClientSalutation()).append(" account ").append(account).append(" ");
            builder.append(account.getBalance()).append("\nenter for continue ");
            io.write(builder.toString());
            io.read();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String printCommandInfo() {
        return "Transfer";
    }

}
