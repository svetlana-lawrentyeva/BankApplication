package com.luxoft.bankapp.service.impl;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 30.01.15
 * Time: 7:54
 */
public class ServiceFactory {
    private volatile static ServiceFactory serviceFactory;
    private volatile static AccountService accountService;
    private volatile static ClientService clientService;
    private volatile static BankService bankService;
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

    public static AccountService getAccountService(){
        if(accountService == null){
            synchronized (monitor){
                if(accountService == null){
                    accountService = new AccountService();
                }
            }
        }
        return accountService;
    }

    public static ClientService getClientService(){
        if(clientService == null){
                    clientService = new ClientService();
        }
        return clientService;
    }

    public static BankService getBankService(){
        if(bankService == null){
                    bankService = new BankService();
        }
        return bankService;
    }


}
