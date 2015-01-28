package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.model.impl.Client;

public class AddAccountCommand extends AbstractCommand {
    public AddAccountCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {  //"client&x&s|c"
        String[] params = param.split("&");
        Client client = null;
        float x = Float.parseFloat(params[1]);
        String message;
        char type = params[2].charAt(0);
        try {
            client = getService().parseFeed(getCommander().getCurrentBank(), params[0]);
            getService().addAccount(client, x, type);
            message = "Current client's active account " + client.getActiveAccount();
        } catch (Exception e) {
            message = e.getMessage();
        }
        setResponse(client, message);
        return getResponse();
    }

    @Override
    public String printCommandInfo() {
        return "Add account";
    }
}
