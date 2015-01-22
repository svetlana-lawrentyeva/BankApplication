package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Commander;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class BankClient {

    Commander commander;

    public static void main(String[] args) {
        BankClient bankClient = new BankClient();
        bankClient.commander = new Commander();
        bankClient.run();
    }

    private Socket socket;

    public void run() {
        try {
            socket = new Socket("localhost", 1999);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        PrintWriter pw = null;
        int i = 0;
        try {
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

                pw.println(request.toString());
                pw.flush();
                System.out.println(br.readLine());

            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
    }
}
