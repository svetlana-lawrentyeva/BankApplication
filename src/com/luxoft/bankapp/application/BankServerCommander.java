package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Commander;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            serverCommander.serverSocket = new ServerSocket(1998);
            serverCommander.socket = serverCommander.serverSocket.accept();
            serverCommander.start();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
