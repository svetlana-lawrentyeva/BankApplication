package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.service.BankInfo;

/**
 * Created by nau on 22.01.15.
 */
public class BankInfoCommand extends AbstractCommand {

    public BankInfoCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {
        BankInfo bankInfo = getService().getBankInfo(getCommander().getCurrentBank());
        String message = "Bank info";
        setResponse(bankInfo, message);
        return getResponse();
    }

    @Override
    public String printCommandInfo() {
        return "Bank info";
    }
}
