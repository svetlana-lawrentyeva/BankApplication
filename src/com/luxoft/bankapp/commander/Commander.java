package com.luxoft.bankapp.commander;

import com.luxoft.bankapp.commander.commands.*;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.*;
import java.util.*;

public class Commander{

    private Client currentClient;
    private Bank currentBank = ServiceFactory.getBankService().getByName("My bank");
    private Map<Integer, Command> commandMap = new HashMap<>();
    private static InputStream is = System.in;
    private static OutputStream os = System.out;
    private static BufferedReader in = new BufferedReader(new InputStreamReader(is));
    private static PrintWriter out = new PrintWriter(new OutputStreamWriter(os));

    public Commander() {
        commandMap.put(0, new FindClientCommand(this, is, os));
        commandMap.put(1, new ShowAllAccounts(this, is, os));
        commandMap.put(2, new DepositCommand(this, is, os));
        commandMap.put(3, new WithdrawCommand(this, is, os));
        commandMap.put(4, new TransferCommand(this, is, os));
        commandMap.put(5, new AddClientCommand(this, is, os));
        commandMap.put(6, new AddAccountCommand(this, is, os));
        commandMap.put(7, new RemoveClientCommand(this, is, os));
        commandMap.put(8, new LoadCommand(this, is, os));
        commandMap.put(9, new SaveCommand(this, is, os));
        commandMap.put(10, new BankInfoCommand(this, is, os));
        commandMap.put(11, new BankFeedCommand(this, is, os));
        commandMap.put(12, new Command() {
            @Override
            public void execute() {
                System.exit(0);
            }

            @Override
            public String printCommandInfo() {
                return "Exit";
            }
        });
    }

    public static void main(String[] args) {
        Commander commander = new Commander();
        while(true){
            Set<Integer>commandSet = commander.commandMap.keySet();
            for(int i = 0; i < commandSet.size(); ++i){
                out.println(i + ". " + commander.commandMap.get(i).printCommandInfo());
            }
            out.println("your choice:");
            out.flush();
            int choice = 0;
            try{
                choice = Integer.parseInt(in.readLine());
            } catch (Exception e){
                e.printStackTrace();
            }
            commander.commandMap.get(choice).execute();
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
        this.commandMap.putAll(map);
    }

    public Map<Integer, Command> getCommandMap() {
        return Collections.unmodifiableMap(commandMap);
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }
}
