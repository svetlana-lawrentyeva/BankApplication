package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.service.impl.ServiceFactory;

/**
 * Created by nau on 22.01.15.
 */
public class BankInfoCommand extends AbstractCommand {

    public BankInfoCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(Io io) {
        try {
            io.write(ServiceFactory.getBankService().getBankInfo(getCommander().getCurrentBank()).toString() + "\nenter for continue ");
            io.read();
        } catch (Exception e) {
            System.out.println("error: "+e.getMessage());
        }
    }

    @Override
    public String printCommandInfo() {
        return "Bank info";
    }
}
