package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.impl.BankImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

    private Commander commander;
    private Bank bank;
    private BankApplication bankApplication;

    public static void main(String[] args) {
        BankServer bankServer = new BankServer();
        bankServer.bankApplication = new BankApplication();
        bankServer.bank = new BankImpl("my bank");
        bankServer.commander = new Commander();
        bankServer.commander.setCurrentBank(bankServer.bank);
        bankServer.bankApplication.initialize(bankServer.bank, bankServer.commander);

        bankServer.run();
    }

    public void run() {
        ServerSocket serverSocket = null;
        Socket connectionSocket = null;
        try {
            serverSocket = new ServerSocket(1999);
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

        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());

            while (true) {
                String param = br.readLine();
                int amp = param.indexOf("&");
                int i = Integer.parseInt(param.substring(0, amp));
                param = param.substring(amp + 1);
                Command command = commander.getCommandMap().get(i);

                String response = (String)command.execute(param);
                pw.println(response);
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (pw != null) {
            pw.close();
        }
    }

}
