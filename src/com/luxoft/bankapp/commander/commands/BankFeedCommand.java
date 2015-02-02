package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.exceptions.BankException;

import java.io.*;

public class BankFeedCommand extends AbstractCommand implements Command {

    public BankFeedCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(InputStream is, OutputStream os) throws IOException, BankException {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(os));
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            out.println("path to feed");
        out.println("");
            out.flush();
            String path = in.readLine();
            getService().loadFeeds(getCommander().getCurrentBank(), path); //"./feeds" - path
            out.println("execute successfully");
        out.println("");
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Feed";
    }
}
