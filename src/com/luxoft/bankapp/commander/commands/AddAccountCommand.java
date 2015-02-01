package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;

public class AddAccountCommand extends AbstractCommand {

    private BufferedReader in;
    private PrintWriter out;
    private InputStream is;
    private OutputStream os;

    public AddAccountCommand(Commander commander, InputStream is, OutputStream os) {
        super(commander);
        this.is = is;
        this.os = os;
        in = new BufferedReader(new InputStreamReader(is));
        out = new PrintWriter(new OutputStreamWriter(os));
    }

    @Override
    public void execute() {
        try {
            out.println("type of account (c|s):");
            out.flush();
            String typeAccount = in.readLine();
            out.println("start balance:");
            out.flush();
            float startBalance = Float.parseFloat(in.readLine());
            Client client = null;
            while((client = getCommander().getCurrentClient()) == null){
                FindClientCommand command = new FindClientCommand(getCommander(), is, os);
                command.execute();
            }
            Account account;
            switch (typeAccount){
                case "c":
                    account = new CheckingAccount();
                    break;
                case "s":
                    account = new SavingAccount();
                    break;
                default:
                    throw new IllegalArgumentException("error: wrong balance type");
            }
            account.setBalance(startBalance);
            client.addAccount(account);
            ServiceFactory.getAccountService().save(account);
            out.println("success");
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.flush();
    }

    @Override
    public String printCommandInfo() {
        return "Add account";
    }
}
