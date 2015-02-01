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
    ObjectInputStream in;
    ObjectOutputStream out;
//    private BufferedReader in = new BufferedReader(new InputStreamReader(is));
//    private PrintWriter out = new PrintWriter(new OutputStreamWriter(os));

    public Commander() {
        try {
            in = new ObjectInputStream(is);
            out = new ObjectOutputStream(os);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
        commander.start();
    }

    public void start(){
        while (true) {
            try {
            Set<Integer> commandSet = commandMap.keySet();
            for (int i = 0; i < commandSet.size(); ++i) {
                out.writeObject((i + ". " + commandMap.get(i).printCommandInfo()));
//                out.println(i + ". " + commandMap.get(i).printCommandInfo());
                out.flush();
            }
                out.writeObject("your choice:");
//            out.println("your choice:");
            out.flush();
            int choice = 0;
                choice = Integer.parseInt((String) in.readObject());
//                choice = Integer.parseInt(in.readLine());
                commandMap.get(choice).execute();
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
//        in = new BufferedReader(new InputStreamReader(is));
        try {
            in = new ObjectInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
//        out = new PrintWriter(new OutputStreamWriter(os));
        try {
            out = new ObjectOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
