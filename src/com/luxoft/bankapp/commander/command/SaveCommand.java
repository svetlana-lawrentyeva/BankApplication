package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;

public class SaveCommand extends AbstractCommand implements Command {
    public SaveCommand(Commander commander) {
        super(commander);
    }

    @Override
    public String execute(String param) {  // "./objects"
        Client currentClient = getCommander().getCurrentClient();
        getService().saveClient(currentClient, param);
        return "Client " + currentClient.getClientSalutation() + " successfully saved";
    }

    @Override
    public String printCommandInfo() {
        return "Save";
    }
}
