package com.luxoft.bankapp.commander.command.servicecommands;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.commander.command.servicecommands.*;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;

import java.util.*;

public class ServiceCommander implements Commander{

    private Client currentClient;
    private Bank currentBank;
    private Map<Integer, Command> commandMap = new HashMap<>();
    private String[] commandRequest = new String[12];

    public ServiceCommander() {
        commandMap.put(0, new FindClientCommand(this));
        commandMap.put(1, new GetActiveAccountCommand(this));
        commandMap.put(2, new WithdrawCommand(this));
        commandMap.put(3, new DepositCommand(this));
        commandMap.put(4, new TransferCommand(this));
        commandMap.put(5, new AddClientCommand(this));
        commandMap.put(6, new RemoveClientCommand(this));
        commandMap.put(7, new BankFeedCommand(this));
        commandMap.put(8, new LoadCommand(this));
        commandMap.put(9, new SaveCommand(this));
        commandMap.put(10, new BankInfoCommand(this));
        commandMap.put(11, new Command() {

            @Override
            public Response execute(String ask) {
                System.exit(0);
                return null;
            }

            @Override
            public String printCommandInfo() {
                return "Exit";
            }
        });

        commandRequest[0] = "name:";
        commandRequest[1] = "";
        commandRequest[2] = "money to withdraw:";
        commandRequest[3] = "money to deposit:";
        commandRequest[4] = "name:&money to transfer:";
        commandRequest[5] = "account type(c|s):&balance:&overdraft:&name:&gender(m|f):&city:";
        commandRequest[6] = "name:";
        commandRequest[7] = "feed, path from:";
        commandRequest[8] = "path to load:";
        commandRequest[9] = "path to save:";
        commandRequest[10] = "";
        commandRequest[11] = "exit";
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }

    public Bank getCurrentBank() {
        return currentBank;
    }

    public void setCurrentBank(Bank currentBank) {
        this.currentBank = currentBank;
    }

    public void registerCommand(Integer name, Command command) {
        commandMap.put(name, command);
    }

    public void removeCommand(String name) {
        commandMap.remove(name);
    }

    public void setCommandMap(Map<Integer, Command> map) {
        this.commandMap.putAll(map);
    }

    public Map<Integer, Command> getCommandMap() {
        return Collections.unmodifiableMap(commandMap);
    }

    public List<String> getCommandRequest() {
        return Arrays.asList(commandRequest);
    }
}
