package com.luxoft.bankapp.application;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.application.io.IoFactory;
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
        serverCommander.commander = new Commander();
        try {
            serverCommander.serverSocket = new ServerSocket(1998);
            serverCommander.initConnection(serverCommander.serverSocket.accept());
            serverCommander.initCommands();
            serverCommander.initIo();
            serverCommander.getCommander().start();
        } catch (IOException e) {
            System.out.println("error: "+e.getMessage());
        }
    }

    public  void initConnection(Socket socket){
            this.socket = socket;
    }

    public void initCommands() {
        Map<Integer, Command> commandMap = new HashMap<>();
        commandMap.put(0, new FindClientCommand(getCommander()));
        commandMap.put(1, new ShowAllAccounts(getCommander()));
        commandMap.put(2, new DepositCommand(getCommander()));
        commandMap.put(3, new WithdrawCommand(getCommander()));
        commandMap.put(4, new TransferCommand(getCommander()));
        commandMap.put(5, new AddClientCommand(getCommander()));
        commandMap.put(6, new RemoveClientCommand(getCommander()));
        commandMap.put(7, new BankInfoCommand(getCommander()));
        commandMap.put(8, new Command() {
            @Override
            public void execute(Io io) {
                io.write("stop");
            }
            @Override
            public String printCommandInfo() {
                return "Exit";
            }
        });
        getCommander().setCommandMap(commandMap);
    }

    public void initIo(){
        Io io = IoFactory.getStream("socket");
        try {
            io.setStreams(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("error: "+e.getMessage());
        }
        getCommander().setIo(io);
    }

    public Commander getCommander() {
        return commander;
    }

    public void setCommander(Commander commander) {
        this.commander = commander;
    }
}
