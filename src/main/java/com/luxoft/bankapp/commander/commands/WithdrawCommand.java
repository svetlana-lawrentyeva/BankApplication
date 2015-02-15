package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public class WithdrawCommand extends AbstractCommand implements Command {

    @Override
    public void execute(Io io) {
        try {
            Client client = null;
            while ((client = getCommander().getCurrentClient()) == null) {
                FindClientCommand command = new FindClientCommand();
                command.execute(io);
            }
            Account account = null;
            while((account = getCommander().getCurrentClient().getActiveAccount()) == null){
                ShowAllAccounts command = new ShowAllAccounts();
                command.execute(io);
            }
            StringBuilder sb = new StringBuilder();

            io.write("money to withdraw:");
            sb.append("money to withdraw:").append(" : ");
            float x = Float.parseFloat(io.read());
            sb.append(String.valueOf(x)).append("\n");
            getServiceFactory().getAccountService().withdraw(account, x);
            String answer = "Current client's active account " +
                    account + " balance: " + account.getBalance() + "\nenter for continue";
            io.write(answer);
            sb.append(answer).append(" : ");
            answer = io.read();
            sb.append(answer).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String printCommandInfo() {
        return "Withdraw";
    }
}
