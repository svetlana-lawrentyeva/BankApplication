package com.luxoft.bankapp.commander;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: nau
 * Date: 1/22/15
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Response implements Serializable {
    private Object object = null;
    private String message = "";

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
