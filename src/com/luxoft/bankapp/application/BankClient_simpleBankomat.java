package com.luxoft.bankapp.application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class BankClient_simpleBankomat {

    private Socket socket;
    private List<Map<String, String>> commands = new ArrayList<>();

    public static void main(String[] args) {
        BankClient_simpleBankomat bankClient = new BankClient_simpleBankomat();
        bankClient.initialize();
        bankClient.run();
    }

    private void initialize(){
        String clientService = "com.luxoft.bankapp.service.impl.ClientService"; // service class that contains method
        String accountService = "com.luxoft.bankapp.service.impl.AccountService";
        String bankService = "com.luxoft.bankapp.service.impl.BankService";

        initMap(clientService,
                "getAllByBank",
                "com.luxoft.bankapp.model.impl.Bank",
                bankService,
                "1",
                "",
                "List<Client>",
                "choose client number:");
        initMap(accountService,
                "getAllByClient",
                "com.luxoft.bankapp.model.impl.Client",
                clientService,
                "",
                "",
                "List<Account>",
                "choose account for operations:");
        initMap(accountService,
                "getBalance",
                "com.luxoft.bankapp.model.Account",
                accountService,
                "",
                "",
                "java.lang.Float",
                "withdraw money:");
        initMap(accountService,
                "withdraw",
                "com.luxoft.bankapp.model.Account",
                accountService,
                "",
                "",
                "",
                "done");
    }

    private void initMap(String serviceClass,
                         String serviceMethod,
                         String entityClass,
                         String entityServiceClass,
                         String entityId,
                         String paramVal,
                         String returnedType,
                         String uiMessage) {
        Map<String, String>map = new HashMap<>();
        map.put("serviceClass", serviceClass);
        map.put("serviceMethod", serviceMethod);
        map.put("entityClass", entityClass);
        map.put("entityServiceClass", entityServiceClass);
        map.put("entityId", entityId);
        map.put("paramVal", paramVal);
        map.put("uiMessage", uiMessage);

        commands.add(map);
    }

    public void run() {
        try {
            socket = new Socket("localhost", 2999);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Scanner sc = new Scanner(System.in);
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            for(int i=0; i < commands.size(); ++i){
                Map<String, String> map = commands.get(i);

                out.writeObject(map);
                out.flush();

                String response = (String) in.readObject();
                System.out.println(response);
                System.out.println(commands.get(i).get("uiMessage"));
                if(i < commands.size() - 2){
                    String entityId = sc.nextLine();
                    commands.get(i + 1).put("entityId", entityId);
                }
                if(i == commands.size() - 2){
                    commands.get(commands.size() - 1).put("entityId", commands.get(commands.size() - 2).get("entityId"));
                    String paramVal = sc.nextLine();
                    commands.get(commands.size() - 1).put("paramVal", paramVal);
                }
            }
            out.writeObject(null);
            out.flush();
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
