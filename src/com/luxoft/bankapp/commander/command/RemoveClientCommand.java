package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.model.impl.Client;

public class RemoveClientCommand extends AbstractCommand{

    public RemoveClientCommand(Commander commander) {
        super(commander);
    }

    @Override public Response execute(String param) {
            String name = param;
            Client client = null;
            String message = "";
            try {
                client = getService().findClient(getCommander().getCurrentBank(), name);
                getService().removeClient(getCommander().getCurrentBank(), client);
                message = "Client " + client.getClientSalutation() + " is deleted";
                getCommander().setCurrentClient(null);
            } catch (Exception e) {
                message = e.getMessage();
            }
            setResponse(client, message);
            return getResponse();
    }

    @Override public String printCommandInfo() {
        return "Remove client";
    }
}
