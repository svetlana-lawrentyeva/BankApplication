package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Client;

public class SetActiveAccountCommand extends AbstractCommand {
    public SetActiveAccountCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {  //"client_name&account_id"
        String message = "";
        Account account = null;
        String[] params = param.split("&");
        try {
            Client client = getService().findClient(getCommander().getCurrentBank(), params[0]);
            long accountId = Long.parseLong(params[1]);
            account = getService().getAccount(client, accountId);
            message = "client " + client.getClientSalutation() + " active account " +
                    getService().getAccountInfo(account);
        } catch (Exception e) {
            message = e.getMessage();
        }
        setResponse(account, message);
        return getResponse();
    }

    @Override
    public String printCommandInfo() {
        return "Set active account";
    }
}
