package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class RemoveClientCommand extends AbstractCommand {

    public RemoveClientCommand(Commander commander) {
        super(commander);
    }

    @Override public Response execute(String param) {
            String name = param;
            Client client = null;
            String message = "";
            try {
                client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), name);
                ServiceFactory.getClientService().remove(client);
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
