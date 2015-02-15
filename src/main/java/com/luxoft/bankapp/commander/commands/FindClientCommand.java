package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class FindClientCommand extends AbstractCommand {

    public void execute(Io io) {
        try {

            io.write("name:");
            
            Client client = getServiceFactory().getClientService().getByName(getCommander().getCurrentBank(), io.read());
            getCommander().setCurrentClient(client);
            io.write("Client " + client.getClientSalutation() + " is checked\nenter for continue");
            io.read();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String printCommandInfo() {
        return "Find client";
    }
}
