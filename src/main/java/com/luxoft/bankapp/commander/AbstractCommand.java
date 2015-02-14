package com.luxoft.bankapp.commander;

import com.luxoft.bankapp.service.ServiceOld;

public abstract class AbstractCommand implements Command {

    private ServiceOld service = new ServiceOld();
    private Commander commander;

    public AbstractCommand(Commander commander) {
        this.commander = commander;
    }

    public ServiceOld getService() {
        return service;
    }

    public void setService(ServiceOld service) {
        this.service = service;
    }

    public Commander getCommander() {
        return commander;
    }

    public void setCommander(Commander commander) {
        this.commander = commander;
    }
}
