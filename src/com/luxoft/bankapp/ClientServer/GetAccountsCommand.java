package com.luxoft.bankapp.ClientServer;


/**
 * Created by SCJP on 15.01.15.
 */
public class GetAccountsCommand implements Command {
    @Override
    public String execute() {
        return ("Accounts of "+ ServerCommander.currentClient+"\n"+bankService.getAccounts(ServerCommander.currentClient));
    }

}
