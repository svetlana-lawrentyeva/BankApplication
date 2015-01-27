package com.luxoft.bankapp.commander.command.daocommands;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.command.AbstractCommand;
import com.luxoft.bankapp.dao.ClientDao;
import com.luxoft.bankapp.dao.impl.ClientDaoImpl;
import com.luxoft.bankapp.model.impl.Client;

public class FindClientCommand extends AbstractCommand {

    ClientDao clientDao = new ClientDaoImpl();

    public FindClientCommand(Commander commander) {
        super(commander);
    }

    public Response execute(String param) {
        String name = param;
        Client client = null;
        String message = "";
        try {
            client = clientDao.findClientByName(getCommander().getCurrentBank(), name);
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
