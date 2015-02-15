package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class LoadCommand extends AbstractCommand implements Command {

    @Override
    public void execute(Io io) {  //"./objects"
        try {
            Client client = null;
            io.write("path:");
            
            client = getServiceFactory().getClientService().loadFromDisk((String) io.read());
            getCommander().setCurrentClient(client);
               io.write(client.toString() + " is loaded\nenter for continue");
            io.read();
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String printCommandInfo() {
        return "Load a client";
    }
}
