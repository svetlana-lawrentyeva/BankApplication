package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class AddClientCommand extends AbstractCommand implements Command {

    private BufferedReader in;
    private PrintWriter out;

    public AddClientCommand(Commander commander, InputStream is, OutputStream os) {
        super(commander);
        in = new BufferedReader(new InputStreamReader(is));
        out = new PrintWriter(new OutputStreamWriter(os));
    }

    @Override
    public void execute() {
        String message;
        try {
            Client client = new Client();
            String value;
            out.println("name:");
            out.flush();
            while (client.getName()=="") {
                client.setName(in.readLine());
            }
            out.println("city:");
            out.flush();
            client.setCity(in.readLine());
            out.println("email:");
            out.flush();
            while (client.getEmail() == "") {
                client.setEmail(in.readLine());
            }
            out.println("phone:");
            out.flush();
            while (client.getPhone() == "") {
                client.setPhone(in.readLine());
            }
            out.println("overdraft:");
            out.flush();
            client.setOverdraft(Float.parseFloat(in.readLine()));
            out.println("gender (m|f):");
            out.flush();
            client.setGender(Gender.getGender(in.readLine()));

            getCommander().getCurrentBank().addClient(client);
            client = ServiceFactory.getClientService().save(client);
            getCommander().setCurrentClient(client);
           out.println("Client " + client.getClientSalutation() + " successfully added");
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.flush();
    }


    @Override
    public String printCommandInfo() {
        return "Add client";
    }
}
