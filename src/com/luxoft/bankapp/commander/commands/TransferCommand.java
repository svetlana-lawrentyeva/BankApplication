package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.model.impl.Client;

public class TransferCommand extends AbstractCommand implements Command {
    public TransferCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {
        String[] params = param.split("&"); //sum&client_name
        String name = params[0];
        float x = Float.parseFloat(params[1]);

        Client client = null;
        StringBuilder message = new StringBuilder();
        try {
            client = getService().findClient(getCommander().getCurrentBank(), name);
        } catch (Exception e) {
            message.append(e.getMessage());
        }
        try {
            getService().transfer(getCommander().getCurrentClient(), client, x);
            message.append("Current client's active account " +
                    getService().getAccountInfo(getCommander().getCurrentClient().getActiveAccount()));
        } catch (Exception e) {
            message.append(":").append(e.getMessage());
        }
        setResponse(client, message.toString());
        return getResponse();
    }

    @Override
    public String printCommandInfo() {
        return "Transfer";
    }
}
