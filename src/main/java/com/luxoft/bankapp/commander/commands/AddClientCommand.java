package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class AddClientCommand extends AbstractCommand implements Command {

    @Override
    public void execute(Io io) {
        try {
            Client client = new Client();
            
            while (client.getName().equals("")) {
                io.write("name:");
                client.setName(io.read());
            }

            io.write("city:");
            client.setCity(io.read());

            while (client.getEmail().equals("")) {
                io.write("email:");
                client.setEmail(io.read());
            }
            
            while (client.getPhone().equals("")) {
                io.write("phone:");
                client.setPhone(io.read());
            }

            io.write("overdraft:");
            client.setOverdraft(Float.parseFloat(io.read()));

            io.write("gender (m|f):");
            client.setGender(Gender.getGender(io.read()));

            getCurrentBank().addClient(client);
            client = getServiceFactory().getClientService().save(client);
            setCurrentClient(client);
            io.write("Client " + client.getClientSalutation() + " successfully added\nenter for continue");
            io.read();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String printCommandInfo() {
        return "Add client";
    }
}
