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
    public void execute(InputStream is, OutputStream os) {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(os));
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            out.println(ServiceFactory.getBankService().getBankInfo(getCommander().getCurrentBank()));
        out.println("");
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Bank info";
    }
}
