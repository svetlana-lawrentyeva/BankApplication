package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class AddAccountCommand extends AbstractCommand {

    public AddAccountCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(Io io) {
        try {
            io.write("type of account (c|s):");
            
            String typeAccount = (String) io.read();
            io.write("start balance:");
            
            float startBalance = Float.parseFloat((String) io.read());
            Client client;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(io);
            }
            Account account;
            switch (typeAccount) {
                case "c":
                    account = new CheckingAccount();
                    break;
                case "s":
                    account = new SavingAccount();
                    break;
                default:
                    throw new IllegalArgumentException("error: wrong balance type");
            }
            account.setClient(client);
            account.setBalance(startBalance);
            client.addAccount(account);
            ServiceFactory.getClientService().save(client);
            io.write("success\nenter for continue");
            io.read();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String printCommandInfo() {
        return "Add account";
    }
}
