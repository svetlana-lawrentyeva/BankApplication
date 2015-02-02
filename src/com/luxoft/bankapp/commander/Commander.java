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
    private InputStream is = System.in;
    private OutputStream os = System.out;
    private BufferedReader in;
    private PrintWriter out;

    public Commander() {

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
            public void execute(InputStream is, OutputStream os) {
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
        commander.start();
    }

    public void start(){try {
            out = new PrintWriter(new OutputStreamWriter(os));
            out.flush();
            in = new BufferedReader(new InputStreamReader(is));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        while (true) {
            try {
            Set<Integer> commandSet = commandMap.keySet();
            for (int i = 0; i < commandSet.size(); ++i) {
                out.println((i + ". " + commandMap.get(i).printCommandInfo()));
                out.flush();
            }
            out.println("your choice:");
            out.println("");
            out.flush();
            int choice = 0;
                choice = Integer.parseInt(in.readLine());
                commandMap.get(choice).execute(is, os);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
