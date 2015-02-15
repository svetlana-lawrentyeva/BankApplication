package com.luxoft.bankapp.service.impl;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 30.01.15
 * Time: 7:54
 */
public class ServiceFactory {
    private volatile static ServiceFactory serviceFactory;
    private volatile AccountService accountService;
    private volatile ClientService clientService;
    private volatile BankService bankService;
    public static Object monitor = new Object();

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        if(serviceFactory == null){
            synchronized (monitor){
                if(serviceFactory == null){
                    serviceFactory = new ServiceFactory();
                }
            }
        }
        return serviceFactory;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public BankService getBankService() {
        return bankService;
    }

    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }
}
