package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.AbstractCommand;

public class BankFeedCommand extends AbstractCommand implements Command {
    public BankFeedCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {
        String message = "";
        try {
            getService().loadFeeds(getCommander().getCurrentBank(), param); //"./feeds" - path
            message = "execute successfully";
        } catch (Exception e) {
            message = e.getMessage();
        }
        setResponse(null, message);
        return getResponse();
    }

    @Override
    public String printCommandInfo() {
        return "Feed";
    }
}
