package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;

public class LoadCommand extends AbstractCommand implements Command {
    public LoadCommand(Commander commander) {
        super(commander);
    }

    @Override
    public String execute(String param) {  //"./objects"
        Client client = getService().loadClient(param);
        getCommander().setCurrentClient(client);
        return client.toString();

    }

    @Override
    public String printCommandInfo() {
        return "Load a client";
    }
}
