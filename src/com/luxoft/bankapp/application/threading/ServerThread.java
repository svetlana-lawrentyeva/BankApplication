package com.luxoft.bankapp.application.threading;

import com.luxoft.bankapp.application.BankServerCommander;
import com.luxoft.bankapp.commander.Commander;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Logger;


public class ServerThread implements Runnable {
    private Socket socket;
    private String id;
    private Logger log = Logger.getLogger(ServerThread.class.getName());

    public ServerThread(Socket socket, String id){
        this.socket = socket;
        this.id = id;
    }
    @Override
    public void run() {
        long connectDate = new Date().getTime();
        log.info("client "+id+" connected to the server on the port "+socket.getPort());
        BankServerCommander serverCommander = new BankServerCommander();
        serverCommander.setCommander(new Commander());
        serverCommander.initConnection(socket);
        BankServerThreaded.getCounter().decrementAndGet();
        serverCommander.initCommands();
        serverCommander.initIo();
        serverCommander.start();
        serverCommander.closeIo();

        log.info("client "+id+" disconnected from the server on the port "+socket.getPort());
        long disconnectDate = new Date().getTime();
        log.info("client "+id+" works for "+(disconnectDate-connectDate)+" ms");
    }
}
