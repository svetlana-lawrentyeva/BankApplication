package com.luxoft.bankapp.application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class BankClient_simple {

    private Socket socket;
    private Map<Integer, List<String>> commandMap = new TreeMap<>();

    public static void main(String[] args) {
        BankClient_simple bankClient = new BankClient_simple();
        bankClient.run();
    }

    private void initialize(){
        List<String>list1 = new ArrayList<>();
        String clientService = "com.luxoft.bankapp.service.impl.ClientService"; // service class that contains method
        String accountService = "com.luxoft.bankapp.service.impl.AccountService";
        String bankService = "com.luxoft.bankapp.service.impl.BankService";

        initMap(1, clientService, "getAllByBank", "choose your name:");
        initMap(2, accountService, "getAllByClient", "choose balance for operations:");
        initMap(3, accountService, "getBalance", "withdraw money:");
    }

    private void initMap(int i, String className, String methodName, String uiMessage){
        List<String>list = new ArrayList<>();
        list.add(className);
        list.add(methodName);
        list.add(uiMessage);
        commandMap.put(i, list);
    }

    public void run() {
        try {
            socket = new Socket("localhost", 2999);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            for(int i:commandMap.keySet()){
                out.writeObject(commandMap.get(i).get(0));
                out.writeObject(commandMap.get(i).get(1));
                out.flush();

                Object object = in.readObject();
                System.out.println(object);
            }


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
