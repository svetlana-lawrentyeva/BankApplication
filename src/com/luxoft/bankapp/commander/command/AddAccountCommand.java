package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.ClientExistsException;

public class AddAccountCommand extends AbstractCommand {
    public AddAccountCommand(Commander commander) {
        super(commander);
    }

    @Override
    public String execute(String param) {  //"client&x&s|c"
        String[] params = param.split("&");
        Client client = null;
        float x = Float.parseFloat(params[1]);
        char type = params[2].charAt(0);
        try {
            client = getService().parseFeed(getCommander().getCurrentBank(), params[0]);
            getService().addAccount(client, x, type);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        return "Current client's active account " +
                client.getActiveAccount();
    }

    @Override
    public String printCommandInfo() {
        return "Add account";
    }
}
