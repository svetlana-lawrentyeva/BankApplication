package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class FindClientCommand extends AbstractCommand {

    public FindClientCommand(Commander commander) {
        super(commander);
    }

    public void execute(InputStream is, OutputStream os) throws IOException, ClientNotExistsException, DaoException {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(os));
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            out.println("name:");
        out.println("");
            out.flush();
            Client client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), in.readLine());
            getCommander().setCurrentClient(client);
            out.println("Client " + client.getClientSalutation() + " is checked");
        out.println("");
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Find client";
    }
}
