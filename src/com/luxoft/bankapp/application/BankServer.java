package com.luxoft.bankapp.application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class BankServer {
    static String bankName = "My bank";

    private Map<String, String>map;

    public static void main(String[] args) {
        BankServer bankServer = new BankServer();
        bankServer.run();
    }

    public void run() {
        ServerSocket serverSocket = null;
        Socket connectionSocket = null;
        try {
            serverSocket = new ServerSocket(2999);
            connectionSocket = serverSocket.accept();

            ObjectInputStream in = null;
            ObjectOutputStream out = null;

            in = new ObjectInputStream(connectionSocket.getInputStream());
            out = new ObjectOutputStream(connectionSocket.getOutputStream());
            out.flush();
            Object request = null;
            while((request = in.readObject())  != null){
                map = (Map<String, String>) request;
                String strServiceClass = map.get("serviceClass");
                String strServiceMethod = map.get("serviceMethod");
                String strEntityClass = map.get("entityClass");
                String strEntityServiceClass = map.get("entityServiceClass");
                String strEntityId = map.get("entityId");
                String strParamVal = map.get("paramVal");

                Class serviceClass = Class.forName(strServiceClass);
                Object service = serviceClass.newInstance();

                Class entityClass = Class.forName(strEntityClass);
                Class entityServiceClass = Class.forName(strEntityServiceClass);
                Object entityService = entityServiceClass.newInstance();
                Method entityGetMethod = entityServiceClass.getMethod("getById", long.class);
                Object entity = entityGetMethod.invoke(entityService, Long.valueOf(strEntityId));

                Class paramClass = null;
                Object paramVal = null;
                Method serviceMethod;
                Object response;
                try{
                    if(!strParamVal.equals("")){
                        paramClass = float.class;
                        serviceMethod = serviceClass.getMethod(strServiceMethod, entityClass, paramClass);
                        response = serviceMethod.invoke(service, entity, Float.valueOf(strParamVal));
                        if (response == null) {
                            response = new String("success");
                        }
                    } else {
                        serviceMethod = serviceClass.getMethod(strServiceMethod, entityClass);
                        response = serviceMethod.invoke(service, entity);
                    }
                }  catch (Exception ex){
                    response = ex.getMessage();
                }
                out.writeObject(getResponse(response));
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                connectionSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getResponse(Object response) {
        StringBuilder builder = new StringBuilder();
        if(response instanceof List){
            List list = (List)response;
            for(Object o:list){
                builder.append(o).append("\n");
            }
        } else {
            builder.append(response);
        }
        return builder.toString();
    }

    private void readCommands(Socket socket) {

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                String param = (String) in.readObject();
                int amp = param.indexOf("&");
                int i = Integer.parseInt(param.substring(0, amp));
                param = param.substring(amp + 1);

                out.writeObject(null);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

}
