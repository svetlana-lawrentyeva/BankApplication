package com.luxoft.bankapp.commander.command;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.service.Service;

public abstract class AbstractCommand implements Command {

    private Service service = new Service();
    private Commander commander;
    private Response response = new Response();

    public AbstractCommand(Commander commander) {
        this.commander = commander;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Commander getCommander() {
        return commander;
    }

    public void setCommander(Commander commander) {
        this.commander = commander;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Object object, String message) {
        response.setObject(object);
        response.setMessage(message);
    }
}