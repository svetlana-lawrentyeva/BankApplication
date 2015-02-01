package com.luxoft.bankapp.application;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BankClientRemoteOffice {

    private Socket socket;

    public static void main(String[] args) {
        BankClientRemoteOffice remoteOffice = new BankClientRemoteOffice();
        remoteOffice.start();
    }

    public void start() {
        InputStream is = null;
        OutputStream os = null;
//        BufferedReader in = null;
//        PrintWriter out = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            socket = new Socket("localhost", 2998);
            os = socket.getOutputStream();
            is = socket.getInputStream();
//            in = new BufferedReader(new InputStreamReader(is));
//            out = new PrintWriter(new OutputStreamWriter(os));
            in = new ObjectInputStream(is);
            out = new ObjectOutputStream(os);
            out.flush();
            Scanner sc = new Scanner(System.in);
            String request = "";
            String response = "";
            while(true){
//                while((response = in.readLine()) != "your choice:"){
                while ((response = (String) in.readObject()) != null) {
                System.out.println(response);
                }
                int i = 0;
                request = sc.nextLine();
                out.writeObject(request);
//                out.println(request);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

        }
    }
}
