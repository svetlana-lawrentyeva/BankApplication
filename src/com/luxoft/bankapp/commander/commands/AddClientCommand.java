package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class AddClientCommand extends AbstractCommand implements Command {

    public AddClientCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(InputStream is, OutputStream os) throws ClientExistsException, DaoException, IOException {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(os));
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String message;
            Client client = new Client();
            String value;
            out.println("name:");
        out.println("");
            out.flush();
            while (client.getName()=="") {
                client.setName(in.readLine());
            }
            out.println("city:");
        out.println("");
            out.flush();
            client.setCity(in.readLine());
            out.println("email:");
        out.println("");
            out.flush();
            while (client.getEmail() == "") {
                client.setEmail(in.readLine());
            }
            out.println("phone:");
        out.println("");
            out.flush();
            while (client.getPhone() == "") {
                client.setPhone(in.readLine());
            }
            out.println("overdraft:");
        out.println("");
            out.flush();
            client.setOverdraft(Float.parseFloat(in.readLine()));
            out.println("gender (m|f):");
        out.println("");
            out.flush();
            client.setGender(Gender.getGender(in.readLine()));

            getCommander().getCurrentBank().addClient(client);
            client = ServiceFactory.getClientService().save(client);
            getCommander().setCurrentClient(client);
           out.println("Client " + client.getClientSalutation() + " successfully added");
        out.flush();
    }


    @Override
    public String printCommandInfo() {
        return "Add client";
    }
}
