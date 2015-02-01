package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class TransferCommand extends AbstractCommand implements Command {

    private BufferedReader in;
    private PrintWriter out;
    private InputStream is;
    private OutputStream os;

    public TransferCommand(Commander commander, InputStream is, OutputStream os) {
        super(commander);
        this.is = is;
        this.os = os;
        in = new BufferedReader(new InputStreamReader(is));
        out = new PrintWriter(new OutputStreamWriter(os));
    }

    @Override
    public void execute() {
        try {
            Client currentClient = null;
            while ((currentClient = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander(), is, os);
                command.execute();
            }
            if(currentClient.getActiveAccount() == null){
                out.println("choose account number:");
                out.flush();
                Command showAccounts = new ShowAllAccounts(getCommander(), is, os);
                showAccounts.execute();
                long idAccount = Long.parseLong(in.readLine());
                currentClient.setActiveAccount(ServiceFactory.getAccountService().getById(idAccount));
            }
            out.println("name of client to transfer:");
            out.flush();
            Client client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), in.readLine());
            if(client.getActiveAccount() == null){
                out.println("choose account number:");
                out.flush();
                Command showAccounts = new ShowAllAccounts(getCommander(), is, os);
                showAccounts.execute();
                long idAccount = Long.parseLong(in.readLine());
                client.setActiveAccount(ServiceFactory.getAccountService().getById(idAccount));
            }
            out.println("money to transfer:");
            out.flush();
            float x = Float.parseFloat(in.readLine());
            ServiceFactory.getAccountService().transfer(currentClient.getActiveAccount(), client.getActiveAccount(), x);
            out.println("Current client's active account " +
                    ServiceFactory.getAccountService().getAccountInfo(getCommander().getCurrentClient().getActiveAccount()));
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Transfer";
    }

}
