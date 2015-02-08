package com.luxoft.bankapp.application.io;

import java.io.*;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 2/5/15
 * Time: 7:32 PM
 */
public class IoSocket implements Io {
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public IoSocket(){
//        System.out.println(Thread.currentThread().getName()+" new io socket created...");
    }

    @Override public void setStreams(InputStream is, OutputStream os){
        try {
            out = new ObjectOutputStream(os);
            out.flush();
            in = new ObjectInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void write(String message){
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public String read(){
        String message;
        try {
            message = (String) in.readObject();
        } catch (IOException e) {
            System.out.println("error: "+e.getMessage());
            message = e.getMessage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            message = "from other side received error: "+e.getMessage();
        }
        return message;
    }

    @Override public void closeStreams(){
        try {
            in.close();
            out.close();
//            System.out.println("streams are closed...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
