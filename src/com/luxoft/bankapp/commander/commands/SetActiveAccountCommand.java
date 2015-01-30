package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

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
            Client client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), params[0]);
            long accountId = Long.parseLong(params[1]);
            account = ServiceFactory.getAccountService().getById(accountId);
            message = "client " + client.getClientSalutation() + " active account " +
                    ServiceFactory.getAccountService().getAccountInfo(account);
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
