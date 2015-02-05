package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class FindClientCommand extends AbstractCommand {

    public FindClientCommand(Commander commander) {
        super(commander);
    }

    public void execute(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClientNotExistsException, DaoException {
        try {

            out.writeObject("name:");
            out.flush();
            Client client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), (String) in.readObject());
            getCommander().setCurrentClient(client);
            out.writeObject("Client " + client.getClientSalutation() + " is checked");
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String printCommandInfo() {
        return "Find client";
    }
}
