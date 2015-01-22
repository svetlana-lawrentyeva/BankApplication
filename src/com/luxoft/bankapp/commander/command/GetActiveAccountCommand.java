package com.luxoft.bankapp.commander.command;


import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;

public class GetActiveAccountCommand extends AbstractCommand implements Command {

    public GetActiveAccountCommand(Commander commander) {
        super(commander);
    }

    @Override
    public String execute(String param) {
        Client currentClient = getCommander().getCurrentClient();
        return ("Active account of " + currentClient.getClientSalutation() + " " + getService().getActiveAccount(currentClient));
    }

    @Override
    public String printCommandInfo() {
        return "Active account info";
    }
}
