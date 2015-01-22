package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.ClientNotExistsException;

public class SetActiveAccountCommand extends AbstractCommand {
    public SetActiveAccountCommand(Commander commander) {
        super(commander);
    }

    @Override
    public String execute(String param) {  //"client_name&account_id"
        String result = "oops";
        String[] params = param.split("&");
        try {
            Client client = getService().findClient(getCommander().getCurrentBank(), params[0]);
            long accountId = Long.parseLong(params[1]);
            Account account = getService().getAccount(client, accountId);
            result = "client " + client.getClientSalutation() + " active account " +
                    getService().getAccountInfo(account);
        } catch (ClientNotExistsException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String printCommandInfo() {
        return "Set active account";
    }
}
