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

    @Override public void setStreams(InputStream is, OutputStream os){
        try {
            out = new ObjectOutputStream(os);
            out.flush();
            in = new ObjectInputStream(is);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override public void write(String message){
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override public String read(){
        String message;
        try {
            message = (String) in.readObject();
        } catch (IOException e) {
            message = e.getMessage();
        } catch (ClassNotFoundException e) {
            message = e.getMessage();
        }
        return message;
    }

    @Override public void closeStreams(){
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
