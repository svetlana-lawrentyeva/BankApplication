package com.luxoft.bankapp.application.threading;

import com.luxoft.bankapp.application.BankServerCommander;
import com.luxoft.bankapp.commander.Commander;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerThread implements Runnable {
    private Socket socket;

    public ServerThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        BankServerCommander serverCommander = new BankServerCommander();
        serverCommander.setCommander(new Commander());
        serverCommander.initConnection(socket);
        BankServerThreaded.getCounter().decrementAndGet();
        serverCommander.initCommands();
        serverCommander.initIo();
        serverCommander.start();
        serverCommander.closeIo();
    }
}
