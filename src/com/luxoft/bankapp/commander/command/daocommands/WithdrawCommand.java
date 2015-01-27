package com.luxoft.bankapp.commander.command.daocommands;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.command.AbstractCommand;

public class WithdrawCommand extends AbstractCommand implements Command {


    public WithdrawCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {
        float x = Float.parseFloat(param);
        StringBuilder message = new StringBuilder();
        try {
            getService().withdraw(getCommander().getCurrentClient(), x);
            message.append("Current client's active account " +
                    getService().getAccountInfo(getCommander().getCurrentClient().getActiveAccount()));
        } catch (Exception e) {
            message.append(e.getMessage()).append(":");
        }
        setResponse(null,message.toString());
        return getResponse();
    }

    @Override
    public String printCommandInfo() {
        return "Withdraw";
    }
}
