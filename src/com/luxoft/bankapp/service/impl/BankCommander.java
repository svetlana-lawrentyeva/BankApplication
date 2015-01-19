package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.impl.BankImpl;
import com.luxoft.bankapp.service.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by SCJP on 15.01.15.
 */
public class BankCommander {
    public static Bank currentBank = new BankImpl("MyBank");
    public static Client currentClient;

//    public static Command[] commands = {
//            new FindClientCommand(), // 1
//            new GetAccountsCommand(), // 2
//            new WithdrawCommand(), // 3
//            new DepositCommand(), // 4
//            new TransferCommand(), // 5
//            new AddClientCommand(), // 6
//            new Command() { // 7 - Exit Command
//                public void execute() {
//                    System.exit(0);
//                }
//                public void printCommandInfo() {
//                    System.out.println("Exit");
//                }
//            }
//    };

    public static Map<String, Command> commandMap = new TreeMap<String, Command>();
    static{
        commandMap.put("1", new FindClientCommand());
        commandMap.put("2", new GetAccountsCommand());
        commandMap.put("3", new WithdrawCommand());
        commandMap.put("4", new DepositCommand());
        commandMap.put("5", new TransferCommand());
        commandMap.put("6", new AddClientCommand());
        commandMap.put("7", new BankFeedCommand());
        commandMap.put("8", new Command(){
            public void execute() {
                System.exit(0);
            }
            public void printCommandInfo() {
                System.out.println("Exit");
            }
        });
    }

    public void registerCommand(String name, Command command){
        commandMap.put(name, command);
    }
    public static void removeCommand(String name){
        commandMap.remove(name);
    }
}
