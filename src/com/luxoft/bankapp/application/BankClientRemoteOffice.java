package com.luxoft.bankapp.application;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.application.io.IoFactory;
import com.luxoft.bankapp.application.threading.BankServerThreaded;

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
            Socket socket = new Socket("localhost", BankServerThreaded.PORT);
            io.setStreams(socket.getInputStream(), socket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            String message;
            while(!(message = io.read()).equals("stop")){
                System.out.println(message);
                io.write(sc.nextLine());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        io.closeStreams();
    }
}
