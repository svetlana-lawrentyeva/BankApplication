package com.luxoft.bankapp.commander.command.servicecommands;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.command.AbstractCommand;
import com.luxoft.bankapp.model.impl.Client;

public class AddClientCommand extends AbstractCommand implements Command {

    public AddClientCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {
        Client client = null;
        String message = "";
        String[] params = param.split("&");
        StringBuilder builder = new StringBuilder();
        builder.append("accounttype=").append(params[0]).append(";");
        builder.append("balance=").append(params[1]).append(";");
        builder.append("overdraft=").append(params[2]).append(";");
        builder.append("name=").append(params[3]).append(";");
        builder.append("gender=").append(params[4]).append(";");
        builder.append("city=").append(params[5]).append(";");
        try {
            client = this.getService().parseFeed(getCommander().getCurrentBank(), builder.toString());
            getCommander().setCurrentClient(client);
            message = "Client " + client.getClientSalutation() + " successfully added";
        } catch (Exception e) {
            message = e.getMessage();
        }
        setResponse(client, message);
        return getResponse();
    }

    @Override
    public String printCommandInfo() {
        return "Add client";
    }
}
