package com.luxoft.bankapp.application.io;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 2/5/15
 * Time: 7:22 PM
 */
public class IoFactory {
    public static Io getStream(String type){
        switch (type){
            case "socket":
                return new IoSocket();
            case "console":
                return new IoConsole();
            default:
                throw new IllegalArgumentException("wrong stream type");
        }
    }
}
