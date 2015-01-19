package com.luxoft.bankapp.ClientServer;

import com.luxoft.bankapp.model.Client;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nau
 * Date: 1/20/15
 * Time: 12:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerCommander {
    static Client currentClient = null;
    static Map<Integer, Command> commandMap;
    static {
        commandMap.put(1, new FindClientCommand());
        commandMap.put(2, new GetAccountsCommand());
        commandMap.put(3, new WithdrawCommand());
        commandMap.put(4, new Command() {
            @Override
            public String execute() {
                System.exit(0);
                return "";
            }
        });
    }
}
