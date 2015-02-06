package com.luxoft.bankapp.application.threading;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.application.io.IoFactory;

import java.net.Socket;
import java.util.Scanner;

/**
 * Created by SCJP on 06.02.15.
 */
public class BankClientMock implements Runnable {

    public int name;
    private String[]commandsArray = {"3", "Mike Greg", "\n", "409", "\n", "1", "\n", "8"};

    public BankClientMock(int name){
        this.name = name;
    }

    @Override
    public void run() {
        Io io = IoFactory.getStream("socket");
        try {
            Socket socket = new Socket("localhost", 1999);
            io.setStreams(socket.getInputStream(), socket.getOutputStream());
            String message;
            for(String command:commandsArray){
                message = io.read();
                System.out.println("thread " + name +" command "+ command);
                io.write(command);
            }
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
        io.closeStreams();
    }
}
