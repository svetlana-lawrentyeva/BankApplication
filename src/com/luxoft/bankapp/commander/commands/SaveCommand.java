package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class SaveCommand extends AbstractCommand implements Command {
    public SaveCommand(Commander commander) {
        super(commander);
    }

    @Override
    public Response execute(String param) {  // "./objects"
        Client currentClient = null;
        String message = null;
        try{
        currentClient = getCommander().getCurrentClient();
        ServiceFactory.getClientService().save(currentClient);
        message = "Client " + currentClient.getClientSalutation() + " successfully saved";
        } catch (Exception e){
            message = e.getMessage();
        }
        setResponse(currentClient, message);
        return getResponse();
    }

    @Override
    public String printCommandInfo() {
        return "Save";
    }
}
