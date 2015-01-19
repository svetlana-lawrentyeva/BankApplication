package com.luxoft.bankapp.ClientServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: nau
 * Date: 1/19/15
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankClient implements Runnable{
    private Socket socket;
    List<String> commands = new ArrayList<>();
    {
        commands.add("1. Find client");
        commands.add("2. Get accounts");
        commands.add("3. Withdraw");
        commands.add("4. Exit");
    }
    public void run(){
        try {
            socket = new Socket("localhost", 1999);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        while(true){
             for(String str:commands){
                 System.out.println(str);
             }

            Scanner sc = new Scanner(System.in);
            System.out.println("choice:");
            int choice = sc.nextInt();
            if(choice < 1 || choice > 3) System.exit(0);
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(choice);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
