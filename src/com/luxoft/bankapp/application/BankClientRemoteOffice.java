package com.luxoft.bankapp.application;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BankClientRemoteOffice {

    private Socket socket;

    public static void main(String[] args) {
        BankClientRemoteOffice remoteOffice = new BankClientRemoteOffice();
        remoteOffice.start();
    }

    public void start() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            socket = new Socket("localhost", 1998);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            Scanner sc = new Scanner(System.in);
            while(true){
                String request = "";
                String response = "";
                while (!(response = (String) in.readObject()).equals("") ) {
                    System.out.println(response);
                }
                request = sc.nextLine();
                out.writeObject(request);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (out != null) {
                try {
                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

        }
    }
}
