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
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("localhost", 1998);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.flush();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);
            while(true){
                String request = "";
                String response = "";
                while (!(response =  in.readLine()).equals("") ) {
                    System.out.println(response);
                }
                request = sc.nextLine();
                out.println(request);
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
