package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class FindClientCommand extends AbstractCommand {

    private BufferedReader in;
    private PrintWriter out;

    public FindClientCommand(Commander commander, InputStream is, OutputStream os) {
        super(commander);
        in = new BufferedReader(new InputStreamReader(is));
        out = new PrintWriter(new OutputStreamWriter(os));
    }

    public void execute() {
        Client client = null;
        try {
            out.println("name:");
            out.flush();
            client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), in.readLine());
            getCommander().setCurrentClient(client);
            out.println("Client " + client.getClientSalutation() + " is checked");
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Find client";
    }
}
