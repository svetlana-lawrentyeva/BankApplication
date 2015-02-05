package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

/**
 * Created by nau on 22.01.15.
 */
public class BankInfoCommand extends AbstractCommand {

    public BankInfoCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(ObjectInputStream in, ObjectOutputStream out) {
        try {
            out.writeObject(ServiceFactory.getBankService().getBankInfo(getCommander().getCurrentBank()));
        out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String printCommandInfo() {
        return "Bank info";
    }
}
