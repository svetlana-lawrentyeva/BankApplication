package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class DepositCommand extends AbstractCommand implements Command {

    @Override
    public void execute(Io io) {
        try {
            Client client = null;
            while ((client = getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand();
                command.execute(io);
            }
            Account account = null;
            while((account = getCurrentClient().getActiveAccount()) == null){
                ShowAllAccounts command = new ShowAllAccounts();
                command.execute(io);
            }
            io.write("money to deposit:");
            
            float x = Float.parseFloat((String) io.read());
            getServiceFactory().getAccountService().deposit(getCurrentClient().getActiveAccount(), x);
            io.write("Current client's active account " +
                    getCurrentClient().getActiveAccount() + " balance: " +
                    getCurrentClient().getActiveAccount().getBalance() + "\nenter for continue");
            io.read();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String printCommandInfo() {
        return "Deposit";
    }
}
