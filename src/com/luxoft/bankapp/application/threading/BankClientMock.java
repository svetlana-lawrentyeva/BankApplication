package com.luxoft.bankapp.application.threading;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.application.io.IoFactory;
import com.luxoft.bankapp.application.io.IoSocket;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.net.Socket;

/**
 * Created by SCJP on 06.02.15.
 */
public class BankClientMock implements Runnable {

    public int name;
    private Client client;
    private String[]commandsArray;

    public BankClientMock(Client client, int name){
//        System.out.println("thread "+name+" start");
        this.client = client;
        this.name = name;
        commandsArray = new String[]{"3", client.getName(), "\n", String.valueOf(client.getActiveAccount().getId()),
                "\n", "1", "\n", "8"};
    }

    @Override
    public void run() {

        Io io = IoFactory.getStream("socket");
        try {
            Socket socket = new Socket("localhost", BankServerThreaded.PORT);
            io.setStreams(socket.getInputStream(), socket.getOutputStream());
            String message;
            for(String command:commandsArray){
                message = io.read();
                io.write(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        io.closeStreams();
    }
}
