package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class FindClientCommand extends AbstractCommand {

    public FindClientCommand(Commander commander) {
        super(commander);
    }

    public Response execute(String param) {
        String name = param;
        Client client = null;
        String message = "";
        try {
            client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), name);
            message = "Client " + client.getClientSalutation() + " is checked";
            getCommander().setCurrentClient(client);
        } catch (Exception e) {
            message = e.getMessage();
        }
        setResponse(client, message);
        return getResponse();
    }

    @Override
    public String printCommandInfo() {
        return "Find client";
    }

}
