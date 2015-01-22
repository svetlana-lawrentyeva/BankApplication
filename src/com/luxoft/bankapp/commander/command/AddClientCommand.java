package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.ClientExistsException;

public class AddClientCommand extends AbstractCommand implements Command {

    public AddClientCommand(Commander commander) {
        super(commander);
    }

    @Override
    public String execute(String param) {
        Client client = null;
        String[] params = param.split("&");
        StringBuilder builder = new StringBuilder();
        builder.append("accounttype=").append(params[0]).append(";");
        builder.append("balance=").append(params[1]).append(";");
        builder.append("overdraft=").append(params[2]).append(";");
        builder.append("name=").append(params[3]).append(";");
        builder.append("gender=").append(params[4]).append(";");
        try {
            client = this.getService().parseFeed(getCommander().getCurrentBank(), builder.toString());
            System.out.println("Client " + client.getClientSalutation() + " successfully added");
            getCommander().setCurrentClient(client);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        return client.toString();
    }

    @Override
    public String printCommandInfo() {
        return "Add client";
    }
}
