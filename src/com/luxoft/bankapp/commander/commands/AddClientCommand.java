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
    public void execute(ObjectInputStream in, ObjectOutputStream out) throws ClientExistsException, DaoException, IOException {
        try {
            Client client = new Client();
            out.writeObject("name:");
            out.flush();
            while (client.getName().equals("")) {
                client.setName((String) in.readObject());
            }
            out.writeObject("city:");
            out.flush();
            client.setCity((String) in.readObject());
            out.writeObject("email:");
            out.flush();
            while (client.getEmail().equals("")) {
                client.setEmail((String) in.readObject());
            }
            out.writeObject("phone:");
            out.flush();
            while (client.getPhone().equals("")) {
                client.setPhone((String) in.readObject());
            }
            out.writeObject("overdraft:");
            out.flush();
            client.setOverdraft(Float.parseFloat((String) in.readObject()));
            out.writeObject("gender (m|f):");
            out.flush();
            client.setGender(Gender.getGender((String) in.readObject()));

            getCommander().getCurrentBank().addClient(client);
            client = ServiceFactory.getClientService().save(client);
            getCommander().setCurrentClient(client);
           out.writeObject("Client " + client.getClientSalutation() + " successfully added");
        out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public String printCommandInfo() {
        return "Add client";
    }
}
