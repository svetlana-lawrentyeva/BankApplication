package com.luxoft.bankapp.ClientServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: nau
 * Date: 1/19/15
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankServer implements Runnable{

   public void run(){
       try (ServerSocket serverSocket = new ServerSocket(1999)){
           Socket connectionSocket = serverSocket.accept();
           readCommands(connectionSocket);
       } catch (IOException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }
   }

    private void readCommands(Socket socket) {
        int choice = 0;
        do{
            try {
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                choice = dis.readInt();
                String responce = ServerCommander.commandMap.get(choice).execute();
                pw.println(responce);
        }catch(IOException e){
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        } while (choice<4 && choice>0);
    }

}
