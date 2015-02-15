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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static Logger log = Logger.getLogger(BankServerCommander.class.getName());

    public static void main(String[] args) {
        BankServerCommander serverCommander = new BankServerCommander();
        log.info("new serverCommander created");
        serverCommander.commander = new Commander();
        log.info("new commander is set");
        try {
            serverCommander.serverSocket = new ServerSocket(1998);
            log.info("serverSocket has created");
            serverCommander.initConnection(serverCommander.serverSocket.accept());
            log.info("connection to a client created");
            serverCommander.initCommands();
            log.info("commands created");
            serverCommander.initIo();
            log.info("IO streams created");
            serverCommander.start();
            log.info("serverCommander started");
        } catch (IOException e) {log.log(Level.SEVERE, e.getMessage(), e);
        }
        serverCommander.commander.closeIo();
        log.info("serverCommander close IO streams");
    }

    public  void initConnection(Socket socket){
            this.socket = socket;
            log.finest("socket "+socket.getPort()+" connected");
    }

    public void initCommands() {
        Map<Integer, Command> commandMap = new HashMap<>();
        log.fine("commandMap created");
        commandMap.put(0, new FindClientCommand());
        log.finest("FindClientCommand added");
        commandMap.put(1, new ShowAllAccounts());
        log.finest("ShowAllAccounts added");
        commandMap.put(2, new DepositCommand());
        log.finest("DepositCommand added");
        commandMap.put(3, new WithdrawCommand());
        log.finest("WithdrawCommand added");
        commandMap.put(4, new TransferCommand());
        log.finest("TransferCommand added");
        commandMap.put(5, new AddClientCommand());
        log.finest("AddClientCommand added");
        commandMap.put(6, new RemoveClientCommand());
        log.finest("RemoveClientCommand added");
        commandMap.put(7, new BankInfoCommand());
        log.finest("BankInfoCommand added");
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
        log.finest("ExitCommand added");
        commander.setCommandMap(commandMap);
        log.fine("commandMap set to commander");
    }

    public void initIo(){
        Io io = IoFactory.getStream("socket");
        log.fine("get socket streams to client");
        try {
            log.info("trying to set IO streams");
            io.setStreams(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(),e);
        }
       commander.setIo(io);
        log.info("IO streams created successfully");
    }

    public Commander getCommander() {
        return commander;
    }

    public void setCommander(Commander commander) {
        this.commander = commander;
    }

    public void start(){
        log.info("commander is starting...");
        commander.start();
    }

    public void closeIo(){
        commander.closeIo();
        log.info("IO streams closed");
    }
}
