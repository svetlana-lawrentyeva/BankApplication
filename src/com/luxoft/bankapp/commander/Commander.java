package com.luxoft.bankapp.commander;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.application.io.IoFactory;
import com.luxoft.bankapp.commander.commands.*;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Commander {

    private Client currentClient;
    private Bank currentBank = ServiceFactory.getBankService().getByName("My bank");
    private Map<Integer, Command> commandMap = new HashMap<>();
    private Io io;

    private void init() {

        commandMap.put(0, new FindClientCommand(this));
        commandMap.put(1, new ShowAllAccounts(this));
        commandMap.put(2, new DepositCommand(this));
        commandMap.put(3, new WithdrawCommand(this));
        commandMap.put(4, new TransferCommand(this));
        commandMap.put(5, new AddClientCommand(this));
        commandMap.put(6, new AddAccountCommand(this));
        commandMap.put(7, new RemoveClientCommand(this));
        commandMap.put(8, new LoadCommand(this));
        commandMap.put(9, new SaveCommand(this));
        commandMap.put(10, new BankInfoCommand(this));
        commandMap.put(11, new BankFeedCommand(this));
        commandMap.put(12, new Command() {
            @Override
            public void execute(Io io) {
                System.exit(0);
            }

            @Override
            public String printCommandInfo() {
                return "Exit";
            }
        });
        io = IoFactory.getStream("console");
        io.setStreams(System.in, System.out);

    }

    public static void main(String[] args) {
        Commander commander = new Commander();
        commander.init();
        commander.start();
        commander.closeIo();
    }

    public void start() {
        boolean _continue = true;
        try {
            while (_continue) {
                StringBuilder command = new StringBuilder();
                Set<Integer> commandSet = commandMap.keySet();
                for (int i = 0; i < commandSet.size(); ++i) {
                    command.append(i).append(". ").append(commandMap.get(i).printCommandInfo()).append("\n");
                }
                command.append("your choice:");
                io.write(command.toString()); // when connection closed server tried to send menu one more time
                int choice = 0;
                String choiceString = io.read();
                choice = Integer.parseInt(choiceString); // and here it tried to read the answer from the client
                commandMap.get(choice).execute(io);
                if(!(choice<commandMap.size()-1)){
                    _continue = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        this.commandMap = map;
    }

    public Map<Integer, Command> getCommandMap() {
        return Collections.unmodifiableMap(commandMap);
    }

    public void setIo(Io io){
        this.io = io;
    }

    public void closeIo(){
        io.closeStreams();
    }
}
