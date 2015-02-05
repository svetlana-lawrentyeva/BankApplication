package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class SaveCommand extends AbstractCommand implements Command {

    public SaveCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(ObjectInputStream in, ObjectOutputStream out) throws ClientExistsException, DaoException, IOException,
            ClientNotExistsException {  // "./objects"
        try {
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(in, out);
            }
            ServiceFactory.getClientService().save(client);
            out.writeObject("Client " + client.getClientSalutation() + " successfully saved");
        out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String printCommandInfo() {
        return "Save";
    }
}
