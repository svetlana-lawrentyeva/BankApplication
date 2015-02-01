package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class SaveCommand extends AbstractCommand implements Command {

    private BufferedReader in;
    private PrintWriter out;
    private InputStream is;
    private OutputStream os;

    public SaveCommand(Commander commander, InputStream is, OutputStream os) {
        super(commander);
        this.is = is;
        this.os = os;
        in = new BufferedReader(new InputStreamReader(is));
        out = new PrintWriter(new OutputStreamWriter(os));
    }

    @Override
    public void execute() {  // "./objects"
        try{
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander(), is, os);
                command.execute();
            }
            ServiceFactory.getClientService().save(client);
            out.println("Client " + client.getClientSalutation() + " successfully saved");
        } catch (Exception e){
            out.println(e.getMessage());
        }
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Save";
    }
}
