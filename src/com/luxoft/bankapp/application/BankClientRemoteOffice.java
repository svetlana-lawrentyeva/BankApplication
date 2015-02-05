package com.luxoft.bankapp.application;

import java.net.Socket;
import java.util.Scanner;

public class BankClientRemoteOffice {

    public static void main(String[] args) {
        BankClientRemoteOffice remoteOffice = new BankClientRemoteOffice();
        remoteOffice.start();
    }

    public void start() {
        Io io = IoFactory.getStream("socket");
        try {
            Socket socket = new Socket("localhost", 1998);
            io.setStreams(socket.getInputStream(), socket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            while(true){
                String message = io.read();
                System.out.println(message);
                io.write(sc.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        io.closeStreams();
    }
}
