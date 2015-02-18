package com.luxoft.bankapp.application;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.application.io.IoFactory;
import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.commands.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BankServerCommander serverCommander = (BankServerCommander) context.getBean("bankServerCommander");
        log.info("new serverCommander created");
        try {
            serverCommander.serverSocket = new ServerSocket(1998);
            log.info("serverSocket has created");
            serverCommander.initConnection(serverCommander.serverSocket.accept());
            log.info("connection to a client created");
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
