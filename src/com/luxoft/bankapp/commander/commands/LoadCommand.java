package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class LoadCommand extends AbstractCommand implements Command {

    private BufferedReader in;
    private PrintWriter out;

    public LoadCommand(Commander commander, InputStream is, OutputStream os) {
        super(commander);
        in = new BufferedReader(new InputStreamReader(is));
        out = new PrintWriter(new OutputStreamWriter(os));
    }

    @Override
    public void execute() {  //"./objects"
        try {
            Client client = null;
            out.println("path:");
            out.flush();
            client = ServiceFactory.getClientService().loadFromDisk(in.readLine());
            getCommander().setCurrentClient(client);
               out.println(client.toString() + " is loaded");
        } catch (Exception e){
           out.println(e.getMessage());
        }
        out.flush();

    }

    @Override
    public String printCommandInfo() {
        return "Load a client";
    }
}
