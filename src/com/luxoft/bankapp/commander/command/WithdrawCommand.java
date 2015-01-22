package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.BankException;

public class WithdrawCommand extends AbstractCommand implements Command {

    public WithdrawCommand(Commander commander) {
        super(commander);
    }

    @Override
    public String execute(String param) {
        float x = Float.parseFloat(param);
        try {
            getService().withdraw(getCommander().getCurrentClient(), x);
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
        return "Current client's active account " +
                getService().getAccountInfo(getCommander().getCurrentClient().getActiveAccount());
    }

    @Override
    public String printCommandInfo() {
        return "Withdraw";
    }
}
