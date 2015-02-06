package com.luxoft.bankapp.application.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 2/5/15
 * Time: 7:44 PM
 */
public interface Io {

    void setStreams(InputStream is, OutputStream os);

    void write(String message);

    String read();

    void closeStreams();
}
