package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class RemoveClientCommand extends AbstractCommand {

    public RemoveClientCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(InputStream is, OutputStream os) throws DaoException, IOException, ClientNotExistsException {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(os));
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
                Client client = null;
                while ((client = getCommander().getCurrentClient()) == null) {
                    FindClientCommand command = new FindClientCommand(getCommander());
                    command.execute(is, os);
                }
                ServiceFactory.getClientService().remove(client);
                out.print("Client " + client.getClientSalutation() + " is deleted");
        out.println("");
                getCommander().setCurrentClient(null);
        out.flush();
    }

    @Override public String printCommandInfo() {
        return "Remove client";
    }
}
