package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.commands.*;

import java.io.IOException;
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
    private Commander commander;
    private ServerSocket serverSocket;
    private Socket socket;

    public static void main(String[] args) {
        BankServerCommander serverCommander = new BankServerCommander();
        try {
            serverCommander.commander = new Commander();
            serverCommander.serverSocket = new ServerSocket(1998);
            serverCommander.socket = serverCommander.serverSocket.accept();
            serverCommander.init();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void init() {
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
            public void execute(Io io) {
                System.exit(0);
            }

            @Override
            public String printCommandInfo() {
                return "Exit";
            }
        });
        commander.setCommandMap(commandMap);
        Io io = IoFactory.getStream("socket");
        try {
            io.setStreams(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        commander.setIo(io);
        commander.start();
    }
}
