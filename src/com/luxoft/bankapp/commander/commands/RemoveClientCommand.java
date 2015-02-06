package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class RemoveClientCommand extends AbstractCommand {

    public RemoveClientCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(Io io) {
        try {
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(io);
            }
            ServiceFactory.getClientService().remove(client);
            io.write("Client " + client.getClientSalutation() + " is deleted\nenter for continue");
            io.read();
            getCommander().setCurrentClient(null);

        } catch (Exception e) {
            System.out.println("error: "+e.getMessage());
        }
    }

    @Override public String printCommandInfo() {
        return "Remove client";
    }
}
