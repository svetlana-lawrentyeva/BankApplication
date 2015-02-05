package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.Io;
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
    public void execute(Io io) {
        try {
            io.write("path to feed");
            
            String path = (String) io.read();
            getService().loadFeeds(getCommander().getCurrentBank(), path); //"./feeds" - path
            io.write("execute successfully");
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String printCommandInfo() {
        return "Feed";
    }
}
