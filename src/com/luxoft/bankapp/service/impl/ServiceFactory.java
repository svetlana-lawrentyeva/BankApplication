package com.luxoft.bankapp.service.impl;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 30.01.15
 * Time: 7:54
 */
public class ServiceFactory {
    private volatile static AccountService accountService;
    private volatile static ClientService clientService;
    private volatile static BankService bankService;
    public static Object accountMonitor = new Object();

    private ServiceFactory(){}

    public static AccountService getAccountService(){
        if(accountService == null){
            synchronized (accountMonitor){
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
