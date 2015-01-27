package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.command.servicecommands.ServiceCommander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.model.impl.Bank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {
    static String bankName = "My bank";

    private ServiceCommander commander;
    private Bank bank;
    private BankApplication bankApplication;

    public static void main(String[] args) {
        BankServer bankServer = new BankServer();
        bankServer.bankApplication = new BankApplication();
        bankServer.bank = new Bank(bankName);
        bankServer.commander = new ServiceCommander();
        bankServer.commander.setCurrentBank(bankServer.bank);
        bankServer.bankApplication.initialize(bankServer.bank, bankServer.commander);

        bankServer.run();
    }

    public void run() {
        ServerSocket serverSocket = null;
        Socket connectionSocket = null;
        try {
            serverSocket = new ServerSocket(2999);
            connectionSocket = serverSocket.accept();
            readCommands(connectionSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                connectionSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readCommands(Socket socket) {

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                String param = (String) in.readObject();
                int amp = param.indexOf("&");
                int i = Integer.parseInt(param.substring(0, amp));
                param = param.substring(amp + 1);
                Command command = commander.getCommandMap().get(i);

                Response response = command.execute(param);
                out.writeObject(response);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

}
