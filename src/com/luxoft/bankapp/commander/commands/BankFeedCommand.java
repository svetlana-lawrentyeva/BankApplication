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
        try {
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(is);
            out.writeObject("path to feed");
            out.flush();
            String path = (String) in.readObject();
            getService().loadFeeds(getCommander().getCurrentBank(), path); //"./feeds" - path
            out.writeObject("execute successfully");
        out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String printCommandInfo() {
        return "Feed";
    }
}
