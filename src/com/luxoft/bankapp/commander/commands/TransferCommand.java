package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class TransferCommand extends AbstractCommand implements Command {

    public TransferCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(InputStream is, OutputStream os) throws DaoException, IOException, BankException {
        PrintWriter out;
        BufferedReader in;
        out = new PrintWriter(new OutputStreamWriter(os));
        out.flush();
        in = new BufferedReader(new InputStreamReader(is));
            Client currentClient = null;
            while ((currentClient = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(is, os);
            }
            if(currentClient.getActiveAccount() == null){
                out.println("choose account number:");
                out.println("");
                out.flush();
                Command showAccounts = new ShowAllAccounts(getCommander());
                showAccounts.execute(is, os);
                long idAccount = Long.parseLong(in.readLine());
                currentClient.setActiveAccount(ServiceFactory.getAccountService().getById(idAccount));
            }
            out.println("name of client to transfer:");
        out.println("");
            out.flush();
            Client client = ServiceFactory.getClientService().getByName(getCommander().getCurrentBank(), in.readLine());
            if(client.getActiveAccount() == null){
                out.println("choose account number:");
                out.println("");
                out.flush();
                Command showAccounts = new ShowAllAccounts(getCommander());
                showAccounts.execute(is, os);
                long idAccount = Long.parseLong(in.readLine());
                client.setActiveAccount(ServiceFactory.getAccountService().getById(idAccount));
            }
            out.println("money to transfer:");
        out.println("");
            out.flush();
            float x = Float.parseFloat(in.readLine());
            ServiceFactory.getAccountService().transfer(currentClient.getActiveAccount(), client.getActiveAccount(), x);
            out.println("Current client's active account " +
                    ServiceFactory.getAccountService().getAccountInfo(getCommander().getCurrentClient().getActiveAccount()));
        out.println("");
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Transfer";
    }

}
