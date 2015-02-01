package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

/**
 * Created by nau on 22.01.15.
 */
public class BankInfoCommand extends AbstractCommand {

    private BufferedReader in;
    private PrintWriter out;

    public BankInfoCommand(Commander commander, InputStream is, OutputStream os) {
        super(commander);
        in = new BufferedReader(new InputStreamReader(is));
        out = new PrintWriter(new OutputStreamWriter(os));
    }

    @Override
    public void execute() {
        try{
            out.println(ServiceFactory.getBankService().getBankInfo(getCommander().getCurrentBank()));
        } catch(Exception e){
            out.println(e.getMessage());
        }
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Bank info";
    }
}
