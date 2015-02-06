package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.BankReport;

public class BankUI{

    private Commander commander;
    private Bank bank;
    private BankApplication bankApplication;

    public static void main(String[] args) {
        BankUI bankUI = new BankUI();
        bankUI.bankApplication = new BankApplication();
        bankUI.bank = new Bank();
        bankUI.bank.setName("My bank");
        bankUI.commander = new Commander();
        bankUI.commander.setCurrentBank(bankUI.bank);
        bankUI.bankApplication.initialize(bankUI.bank, bankUI.commander);


        if(args.length>0){
            for(String str:args){
                if(str.equals("report")){
                    BankReport bankReport = new BankReport();
                    bankReport.setBank(bankUI.bank);
                    bankReport.getNumberOfClients();
                    bankReport.getAccountsNumber();
                    bankReport.getClientsSorted();
                    bankReport.getBankCreditSum();
                    bankReport.getClientsByCity();
                }
            }
        } else {
            while (true) {
                Client currentClient = bankUI.commander.getCurrentClient();
                System.out.println("-----------------------------------------------------");
                System.out.println(bankUI.commander.getCurrentBank());
                if(currentClient != null){
                    System.out.println("Active client is "+currentClient+"\n");
                }
                for(Integer i:bankUI.commander.getCommandMap().keySet()){
                    System.out.print(i + ". ");
                    System.out.println(bankUI.commander.getCommandMap().get(i).printCommandInfo());
                }
            }
        }
    }
}
