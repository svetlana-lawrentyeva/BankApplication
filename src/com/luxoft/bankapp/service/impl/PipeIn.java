package com.luxoft.bankapp.service.impl;

import java.io.*;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 2/2/15
 * Time: 1:26 AM
 */
public class PipeIn extends InputStream {
    BufferedReader in;
    ObjectOutputStream out;

    public PipeIn(InputStream is, ObjectOutputStream out){
        this.in = new BufferedReader(new InputStreamReader(is));
        this.out = out;
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override public int read() throws IOException {
        String str = in.readLine();
        out.writeObject(str);
        out.flush();
        return 1;
    }
}
