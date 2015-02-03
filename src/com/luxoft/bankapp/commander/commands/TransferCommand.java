package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class TransferCommand extends AbstractCommand implements Command {

    public TransferCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(InputStream is, OutputStream os) throws DaoException, IOException, BankException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(is);
        out.flush();
            Client currentClient = null;
            while ((currentClient = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(is, os);
            }
            if(currentClient.getActiveAccount() == null){
                out.writeObject("choose account number:");
                out.flush();
                Command showAccounts = new ShowAllAccounts(getCommander());
                showAccounts.execute(is, os);
                long idAccount = Long.parseLong((String) in.readObject());
                currentClient.setActiveAccount(ServiceFactory.getAccountService().getById(idAccount));
            }
            out.writeObject("name of client to transfer:");
            out.flush();
            Client client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), (String) in.readObject());
            if(client.getActiveAccount() == null){
                out.writeObject("choose account number:");
                out.flush();
                Command showAccounts = new ShowAllAccounts(getCommander());
                showAccounts.execute(is, os);
                long idAccount = Long.parseLong((String) in.readObject());
                client.setActiveAccount(ServiceFactory.getAccountService().getById(idAccount));
            }
            out.writeObject("money to transfer:");
            out.flush();
            float x = Float.parseFloat((String) in.readObject());
            ServiceFactory.getAccountService().transfer(currentClient.getActiveAccount(), client.getActiveAccount(), x);
            out.writeObject("Current client's active account " +
                    ServiceFactory.getAccountService().getAccountInfo(getCommander().getCurrentClient().getActiveAccount()));
        out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String printCommandInfo() {
        return "Transfer";
    }

}
