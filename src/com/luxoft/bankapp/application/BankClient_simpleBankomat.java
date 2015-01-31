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
        bankClient.run();
    }

    private void initialize(){
        List<String>list1 = new ArrayList<>();
        String clientService = "com.luxoft.bankapp.service.impl.ClientService"; // service class that contains method
        String accountService = "com.luxoft.bankapp.service.impl.AccountService";

        initMap(clientService, "getAllByBank", "com.luxoft.bankapp.model.impl.Bank", "setId", "2", "List<Client>", "choose your name:");
        initMap(accountService, "getAllByClient", "java.lang.String", "", "", "List<Account>",  "choose balance for operations:");
        initMap(accountService, "getBalance", "long.class", "setId", "", "float.class", "withdraw money:");
    }

    private void initMap(String className, String methodName, String parameterType, String setterName, String fieldValue,
                         String returnedType, String uiMessage){
        Map<String, String>map = new HashMap<>();
        map.put("class", className);
        map.put("methodName", methodName);
        map.put("parameterType", parameterType);
        map.put("fieldName", setterName);
        map.put("fieldValue", fieldValue);
        map.put("returnedType", returnedType);
        map.put("uiMessage", uiMessage);

        commands.add(map);
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

            for(int i=0; i< commands.size(); ++i){
                Map<String, String> map = commands.get(i);

                out.writeObject(map);
                out.flush();

                map = (Map<String, String>) in.readObject();
                System.out.println(map.get("returnedType"));
                System.out.println(map.get("uiMessage"));

                if(i < commands.size()-1){
                }
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
