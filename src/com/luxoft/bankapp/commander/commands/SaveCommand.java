package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class SaveCommand extends AbstractCommand implements Command {

    public SaveCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(Io io) {  // "./objects"
        try {
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(io);
            }
            ServiceFactory.getClientService().save(client);
            io.write("Client " + client.getClientSalutation() + " successfully saved\nenter for continue");
            io.read();
        
        } catch (Exception e) {
            System.out.println("error: "+e.getMessage());
        }
    }

    @Override
    public String printCommandInfo() {
        return "Save";
    }
}
