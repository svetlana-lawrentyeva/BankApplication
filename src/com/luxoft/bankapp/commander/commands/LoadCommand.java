package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.Io;
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
    public void execute(Io io) {  //"./objects"
        try {
            Client client = null;
            io.write("path:");
            
            client = ServiceFactory.getClientService().loadFromDisk((String) io.read());
            getCommander().setCurrentClient(client);
               io.write(client.toString() + " is loaded\nenter for continue");
            io.read();
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String printCommandInfo() {
        return "Load a client";
    }
}
