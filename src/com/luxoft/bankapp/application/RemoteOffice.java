package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class RemoteOffice {

    Commander commander;

    public static void main(String[] args) {
        RemoteOffice remoteOffice = new RemoteOffice();
        remoteOffice.commander = new Commander();
        remoteOffice.run();
    }

    private Socket socket;

    public void run() {
        try {
            socket = new Socket("localhost", 2999);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        int i = 0;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            do {
                for (int j = 0; j < commander.getCommandRequest().size(); ++j) {
                    System.out.println("" + j + ". " + commander.getCommandMap().get(j).printCommandInfo());
                }
                Scanner sc = new Scanner(System.in);
                System.out.println("choice:");
                String choice = sc.nextLine();
                i = Integer.parseInt(choice);

                StringBuilder request = new StringBuilder();
                String param = commander.getCommandRequest().get(i);
                String[] params = param.split("&");
                request.append(i).append("&");
                for (int j = 0; j < params.length; ++j) {
                    if (!params[j].equals("")) {
                        System.out.println(params[j]);
                        request.append(sc.nextLine());
                    } else {
                        request.append(" ");
                    }
                    if (j != params.length - 1) {
                        request.append("&");
                    }
                }

                out.writeObject(request.toString());
                out.flush();
                Response response = (Response) in.readObject();
                System.out.println(response.getMessage());
                if(response.getObject() != null){
                    System.out.println(response.getObject().toString());
                }

            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }
}
