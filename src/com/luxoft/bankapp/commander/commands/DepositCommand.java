package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class DepositCommand extends AbstractCommand implements Command {

    private BufferedReader in;
    private PrintWriter out;
    private InputStream is;
    private OutputStream os;

    public DepositCommand(Commander commander, InputStream is, OutputStream os) {
        super(commander);
        this.is = is;
        this.os = os;
        in = new BufferedReader(new InputStreamReader(is));
        out = new PrintWriter(new OutputStreamWriter(os));
    }

    @Override
    public void execute() {
        try {
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander(), is, os);
                command.execute();
            }
            out.println("choose account number:");
            out.flush();
            Command showAccounts = new ShowAllAccounts(getCommander(), is, os);
            showAccounts.execute();
            long idAccount = Long.parseLong(in.readLine());
            getCommander().getCurrentClient().setActiveAccount(ServiceFactory.getAccountService().getById(idAccount));
            out.println("money to deposit:");
            out.flush();
            float x = Float.parseFloat(in.readLine());
            ServiceFactory.getAccountService().deposit(getCommander().getCurrentClient().getActiveAccount(), x);
            out.println("Current client's active account " +
                    getCommander().getCurrentClient().getActiveAccount() + "balance: " +
                    getCommander().getCurrentClient().getActiveAccount().getBalance());
        } catch (Exception e){
            out.println(e.getMessage());
        }
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Deposit";
    }
}
