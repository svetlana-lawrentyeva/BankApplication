package com.luxoft.bankapp.commander.command.daocommands;


import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.command.AbstractCommand;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Client;

public class GetActiveAccountCommand extends AbstractCommand implements Command {

    public GetActiveAccountCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {
        String message = "";
        Client currentClient = getCommander().getCurrentClient();
        Account account = null;
        try {
            account = getService().getActiveAccount(currentClient);
            message = "Active account of " + currentClient.getClientSalutation() + " " + account;
        } catch (Exception e){
            message = e.getMessage();
        }
        setResponse(account, message);
        return getResponse();
    }

    @Override
    public String printCommandInfo() {
        return "Active account info";
    }
}
