package com.luxoft.bankapp.application.threading;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.application.io.IoFactory;
import com.luxoft.bankapp.application.io.IoSocket;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class BankClientMock implements Callable<Long> {

    public int name;
    private Client client;
    private String[]commandsArray;
    private Map<String, String>commandsMap = new HashMap<>();

    public BankClientMock(Client client, int name){
        this.client = client;
        this.name = name;

        commandsMap.put("menu1","3");
        commandsMap.put("name:",client.getName());
        commandsMap.put("enter","\n");
        commandsMap.put("choose",String.valueOf(client.getActiveAccount().getId()));
        commandsMap.put("money to withdraw:", "1");
        commandsMap.put("menu2", "8");
    }

    @Override
    public Long call() throws Exception {
        Socket socket = null;
        Io io = IoFactory.getStream("socket");
        Long timeBegin = new Date().getTime();
        try {
            socket = new Socket("localhost", BankServerThreaded.PORT);
            io.setStreams(socket.getInputStream(), socket.getOutputStream());
            String message;
            StringBuilder builder = new StringBuilder();
            builder.append("thread ").append(name);
            int menuCounter = 1;
            String command;
            do{
                message = io.read();

                message=(message.length()>100)?"menu"+menuCounter++:message;
                message=(message.endsWith("enter for continue"))?"enter":message;
                message=(message.startsWith("choose account number"))?"choose":message;

                command = commandsMap.get(message);
                command=(command.equals("\n"))?"enter":command;
                io.write(command);

            } while (!message.equals("menu2"));
            System.out.println(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        io.closeStreams();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long timeFinish = new Date().getTime();
        return timeFinish-timeBegin;
    }
}
