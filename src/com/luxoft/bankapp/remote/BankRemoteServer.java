package com.luxoft.bankapp.remote;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: nau
 * Date: 1/22/15
 * Time: 12:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class BankRemoteServer{

    Socket connection;
    ObjectInputStream in;
    ObjectOutputStream out;
    String message;
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter stdout = new PrintWriter(new OutputStreamWriter(System.out));

    public void readMessage() {
        try {
            message = (String) in.readObject();
            stdout.println("client>" + message);
            stdout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        try {
            message = stdin.readLine();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BankRemoteServer bankRemoteServer= new BankRemoteServer();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1999);
            bankRemoteServer.connection = serverSocket.accept();

            bankRemoteServer.out = new ObjectOutputStream(bankRemoteServer.connection.getOutputStream());
            bankRemoteServer.out.flush();
            bankRemoteServer.in = new ObjectInputStream(bankRemoteServer.connection.getInputStream());

        while(true) {
            bankRemoteServer.readMessage();
            bankRemoteServer.sendMessage();
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
