package com.luxoft.bankapp.commander;

import com.luxoft.bankapp.service.ServiceOld;
import com.luxoft.bankapp.service.impl.ServiceFactory;

public abstract class AbstractCommand implements Command {

    private ServiceOld service = new ServiceOld();
    private Commander commander;
    private ServiceFactory serviceFactory;

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

    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
}
