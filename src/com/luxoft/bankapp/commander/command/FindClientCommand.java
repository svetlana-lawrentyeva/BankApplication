package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.ClientNotExistsException;

public class FindClientCommand extends AbstractCommand implements Command {

    public FindClientCommand(Commander commander) {
        super(commander);
    }

    public String execute(String param) {
        String name = param;
        Client client = null;
        try {
            client = getService().findClient(getCommander().getCurrentBank(), name);
        } catch (ClientNotExistsException e) {
            System.out.println(e.getMessage());
        }
        getCommander().setCurrentClient(client);
        return ("Client " + client.getClientSalutation() + " is checked");
    }

    @Override
    public String printCommandInfo() {
        return "Find client";
    }

}
