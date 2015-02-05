package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class RemoveClientCommand extends AbstractCommand {

    public RemoveClientCommand(Commander commander) {
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
                ServiceFactory.getClientService().remove(client);
                out.writeObject("Client " + client.getClientSalutation() + " is deleted");
                getCommander().setCurrentClient(null);
        out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override public String printCommandInfo() {
        return "Remove client";
    }
}
