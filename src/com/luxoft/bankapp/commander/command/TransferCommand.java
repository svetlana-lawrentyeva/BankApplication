package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.BankException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.ClientNotExistsException;

public class TransferCommand extends AbstractCommand implements Command {
    public TransferCommand(Commander commander) {
        super(commander);
    }

    @Override
    public String execute(String param) {
        String[] params = param.split("&"); //sum&client_name

        String name = params[0];
        float x = Float.parseFloat(params[1]);

        Client client = null;
        try {
            client = getService().findClient(getCommander().getCurrentBank(), name);
        } catch (ClientNotExistsException e) {
            System.out.println(e.getMessage());
        }
        try {
            getService().transfer(getCommander().getCurrentClient(), client, x);
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
        return "Current client's active account " +
                getService().getAccountInfo(getCommander().getCurrentClient().getActiveAccount());
    }

    @Override
    public String printCommandInfo() {
        return "Transfer";
    }
}
