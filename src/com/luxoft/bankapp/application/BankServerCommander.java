package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.commands.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 2/1/15
 * Time: 11:34 PM
 */
public class BankServerCommander {
    Commander commander;
    ServerSocket serverSocket;
    Socket socket;

    public static void main(String[] args) {
        BankServerCommander serverCommander = new BankServerCommander();
        try {
            serverCommander.commander = new Commander();
            serverCommander.init();
            serverCommander.serverSocket = new ServerSocket(1998);
            serverCommander.socket = serverCommander.serverSocket.accept();
            serverCommander.start();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void init(){
        Map<Integer, Command> commandMap = new HashMap<>();
        commandMap.put(0, new FindClientCommand(commander));
        commandMap.put(1, new ShowAllAccounts(commander));
        commandMap.put(2, new DepositCommand(commander));
        commandMap.put(3, new WithdrawCommand(commander));
        commandMap.put(4, new TransferCommand(commander));
        commandMap.put(5, new AddClientCommand(commander));
        commandMap.put(6, new RemoveClientCommand(commander));
        commandMap.put(7, new BankInfoCommand(commander));
        commandMap.put(8, new Command() {
            @Override
            public void execute(InputStream is, OutputStream os) {
                System.exit(0);
            }

            @Override
            public String printCommandInfo() {
                return "Exit";
            }
        });
        commander.setCommandMap(commandMap);
    }

    private void start(){
        try {
            commander.setIs(socket.getInputStream());
            commander.setOs(socket.getOutputStream());
            commander.start();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
