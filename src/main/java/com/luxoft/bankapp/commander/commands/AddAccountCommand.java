package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddAccountCommand extends AbstractCommand {
    private final Logger log = Logger.getLogger(AddAccountCommand.class.getName());
    private FindClientCommand findClientCommand;

    @Override
    public void execute(Io io) {
        try {
            io.write("type of account (c|s):");
            
            String typeAccount = (String) io.read();
            io.write("start balance:");
            
            float startBalance = Float.parseFloat((String) io.read());
            Client client;
            while ((client = getCommander().getCurrentClient()) == null) {
                findClientCommand.execute(io);
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
            getServiceFactory().getClientService().save(client);
            io.write("success\nenter for continue");
            io.read();
            
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public String printCommandInfo() {
        return "Add account";
    }

    public FindClientCommand getFindClientCommand() {
        return findClientCommand;
    }

    public void setFindClientCommand(FindClientCommand findClientCommand) {
        this.findClientCommand = findClientCommand;
    }
}
