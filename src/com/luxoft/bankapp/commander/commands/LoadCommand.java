package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class LoadCommand extends AbstractCommand implements Command {

    public LoadCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(ObjectInputStream in, ObjectOutputStream out) throws IOException, BankException {  //"./objects"
        try {
            Client client = null;
            out.writeObject("path:");
            out.flush();
            client = ServiceFactory.getClientService().loadFromDisk((String) in.readObject());
            getCommander().setCurrentClient(client);
               out.writeObject(client.toString() + " is loaded");
        out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String printCommandInfo() {
        return "Load a client";
    }
}
