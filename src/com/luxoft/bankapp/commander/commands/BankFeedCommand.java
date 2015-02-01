package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;

import java.io.*;

public class BankFeedCommand extends AbstractCommand implements Command {

    private BufferedReader in;
    private PrintWriter out;

    public BankFeedCommand(Commander commander, InputStream is, OutputStream os) {
        super(commander);
        in = new BufferedReader(new InputStreamReader(is));
        out = new PrintWriter(new OutputStreamWriter(os));
    }

    @Override
    public void execute() {
        try {
            out.println("path to feed");
            out.flush();
            String path = in.readLine();
            getService().loadFeeds(getCommander().getCurrentBank(), path); //"./feeds" - path
            out.println("execute successfully");
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Feed";
    }
}
