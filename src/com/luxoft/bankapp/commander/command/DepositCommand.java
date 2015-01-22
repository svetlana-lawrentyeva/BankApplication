package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;

public class DepositCommand extends AbstractCommand implements Command {
    public DepositCommand(Commander commander) {
        super(commander);
    }

    @Override
    public String execute(String param) {
        float x = Float.parseFloat(param);
        getService().deposit(getCommander().getCurrentClient(), x);
        return "Current client's active account " +
                getService().getAccountInfo(getCommander().getCurrentClient().getActiveAccount());

    }

    @Override
    public String printCommandInfo() {
        return "Deposit";
    }
}
