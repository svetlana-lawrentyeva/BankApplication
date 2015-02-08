package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class WithdrawCommand extends AbstractCommand implements Command {

    public WithdrawCommand(Commander commander) {
        super(commander);
    }

    @Override
    public void execute(Io io) {
        try {
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand(getCommander());
                command.execute(io);
            }
            Account account = null;
            while((account = getCommander().getCurrentClient().getActiveAccount()) == null){
                ShowAllAccounts command = new ShowAllAccounts(getCommander());
                command.execute(io);
            }
            io.write("money to withdraw:");
            float x = Float.parseFloat((String) io.read());
            ServiceFactory.getAccountService().withdraw(getCommander().getCurrentClient().getActiveAccount(), x);
            io.write("Current client's active account " +
                    getCommander().getCurrentClient().getActiveAccount() + " balance: " +
                    getCommander().getCurrentClient().getActiveAccount().getBalance() + "\nenter for continue ");
            io.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String printCommandInfo() {
        return "Withdraw";
    }
}
