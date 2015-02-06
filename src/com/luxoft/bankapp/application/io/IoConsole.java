package com.luxoft.bankapp.application.io;

import java.io.*;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 2/5/15
 * Time: 7:43 PM
 */
public class IoConsole implements Io {
    private PrintWriter out;
    private BufferedReader in;

    @Override public void setStreams(InputStream is, OutputStream os) {
//        out = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(os)));
        out = new PrintWriter(new OutputStreamWriter(os), true);
        in = new BufferedReader(new InputStreamReader(is));
    }

    @Override public void write(String message) {
        out.println(message);
    }

    @Override public String read() {
        String message;
        try {
            message = in.readLine();
        } catch (IOException e) {
            message = e.getMessage();
        }
        return message;
    }

    @Override public void closeStreams() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
