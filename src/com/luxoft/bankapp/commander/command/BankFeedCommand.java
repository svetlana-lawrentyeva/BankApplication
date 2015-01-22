package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.ClientExistsException;

public class BankFeedCommand extends AbstractCommand implements Command {
    public BankFeedCommand(Commander commander) {
        super(commander);
    }

    @Override
    public String execute(String param) {
        String result = "oops";
        try {
            getService().loadFeeds(getCommander().getCurrentBank(), param); //"./feeds" - path
            result = "execute successfully";
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String printCommandInfo() {
        return "Feed";
    }
}
