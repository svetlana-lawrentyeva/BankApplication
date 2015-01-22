package com.luxoft.bankapp.remote;

import com.luxoft.bankapp.model.impl.Client;

import java.io.*;
import java.net.Socket;

public class BankRemoteOffice {

    private static final String SERVER = "localhost";
    Socket connection;
    ObjectInputStream in;
    ObjectOutputStream out;
    String message;
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter stdout = new PrintWriter(new OutputStreamWriter(System.out));


    public BankInfo getBankInfo(){
        BankInfo bankInfo = new BankInfo();

        return bankInfo;
    }

    public Client getClient(String name){
        Client client = new Client();

        return client;
    }

    public void addClient(Client client){

    }

    public void deleteClient(Client client){

    }

    public void readMessage() {
        try {
            message = (String) in.readObject();
            stdout.println("server>" + message);
            stdout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(){
        try {
            message = stdin.readLine();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BankRemoteOffice bankRemoteOffice = new BankRemoteOffice();
        try {
            bankRemoteOffice.connection = new Socket(SERVER, 1999);

            bankRemoteOffice.out = new ObjectOutputStream(bankRemoteOffice.connection.getOutputStream());
            bankRemoteOffice.out.flush();
            bankRemoteOffice.in = new ObjectInputStream(bankRemoteOffice.connection.getInputStream());

            do {
                bankRemoteOffice.sendMessage();
                bankRemoteOffice.readMessage();
            } while(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
