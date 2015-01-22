package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;

/**
 * Created by nau on 22.01.15.
 */
public class BankInfoCommand extends AbstractCommand {

    public BankInfoCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Object execute(String param) {
        return getService().getBankInfo(getCommander().getCurrentBank());
    }

    @Override
    public String printCommandInfo() {
        return "Bank info";
    }
}
