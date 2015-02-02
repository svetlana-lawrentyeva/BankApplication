package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class LoadCommand extends AbstractCommand implements Command {

    public LoadCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(InputStream is, OutputStream os) throws IOException, BankException {  //"./objects"
        PrintWriter out = new PrintWriter(new OutputStreamWriter(os));
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
            Client client = null;
            out.println("path:");
        out.println("");
            out.flush();
            client = ServiceFactory.getClientService().loadFromDisk(in.readLine());
            getCommander().setCurrentClient(client);
               out.println(client.toString() + " is loaded");
        out.println("");
        out.flush();

    }

    @Override
    public String printCommandInfo() {
        return "Load a client";
    }
}
