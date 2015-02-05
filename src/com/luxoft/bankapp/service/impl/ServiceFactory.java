package com.luxoft.bankapp.service.impl;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 30.01.15
 * Time: 7:54
 */
public class ServiceFactory {
    private static AccountService accountService;
    private static ClientService clientService;
    private static BankService bankService;

    private ServiceFactory(){}

    public static AccountService getAccountService(){
        if(accountService == null){
            accountService = new AccountService();
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
