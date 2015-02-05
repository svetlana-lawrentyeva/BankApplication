package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.Io;
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

    public void execute(Io io) {
        try {

            io.write("name:");
            
            Client client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), io.read());
            getCommander().setCurrentClient(client);
            io.write("Client " + client.getClientSalutation() + " is checked\nenter for continue");
            io.read();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String printCommandInfo() {
        return "Find client";
    }
}
