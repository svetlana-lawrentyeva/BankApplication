package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class DepositCommand extends AbstractCommand implements Command {
    public DepositCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {
        String message = "";
        float x = Float.parseFloat(param);
        try{
        ServiceFactory.getAccountService().deposit(getCommander().getCurrentClient().getActiveAccount(), x);
        message = "Current client's active account " +
                ServiceFactory.getAccountService().getAccountInfo(getCommander().getCurrentClient().getActiveAccount());
        } catch (Exception e){
            message = e.getMessage();
        }
        setResponse(null, message);
        return getResponse();

    }

    @Override
    public String printCommandInfo() {
        return "Deposit";
    }
}
