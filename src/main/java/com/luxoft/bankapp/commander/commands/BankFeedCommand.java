package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;

public class BankFeedCommand extends AbstractCommand implements Command {

    @Override
    public void execute(Io io) {
        try {
            io.write("path to feed");
            
            String path = (String) io.read();
            getService().loadFeeds(getCommander().getCurrentBank(), path); //"./feeds" - path
            io.write("execute successfully");
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String printCommandInfo() {
        return "Feed";
    }
}
