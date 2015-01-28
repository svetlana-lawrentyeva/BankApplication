package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.model.impl.Client;

public class LoadCommand extends AbstractCommand implements Command {
    public LoadCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {  //"./objects"
        Client client = null;
        String message = "";
        try{
        client = getService().loadClient(param);
        getCommander().setCurrentClient(client);
            message = client.toString() + " is loaded";
        } catch (Exception e){
            message = e.getMessage();
        }
        setResponse(client, message);
        return getResponse();

    }

    @Override
    public String printCommandInfo() {
        return "Load a client";
    }
}
