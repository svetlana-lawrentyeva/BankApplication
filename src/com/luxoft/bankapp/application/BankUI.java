package com.luxoft.bankapp.application;

import com.luxoft.bankapp.commander.Command;
import com.luxoft.bankapp.commander.Commander;
import com.luxoft.bankapp.commander.Response;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.BankReport;
import com.luxoft.bankapp.model.impl.Client;

import java.util.Scanner;

public class BankUI{

    private Commander commander;
    private Bank bank;
    private BankApplication bankApplication;

    public static void main(String[] args) {
        BankUI bankUI = new BankUI();
        bankUI.bankApplication = new BankApplication();
        bankUI.bank = new Bank("my bank");
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
                if(currentClient != null)
                    System.out.println("Active client is "+currentClient+"\n");
                Command command;
                for(Integer i:bankUI.commander.getCommandMap().keySet()){
                    System.out.print(i + ". ");
                    System.out.println(bankUI.commander.getCommandMap().get(i).printCommandInfo());
                }
                Scanner sc = new Scanner((System.in));
                int i = Integer.parseInt(sc.nextLine());

                String param = bankUI.commander.getCommandRequest().get(i);
                String[]params = param.split("&");
                StringBuilder builder = new StringBuilder();
                for(int j = 0; j < params.length; ++j){
                    if(!params[j].equals("")){
                        System.out.println(params[j]);
                        builder.append(sc.nextLine());
                        if(j<params.length-1) {
                            builder.append("&");
                        }
                    }
                }
                Response response = bankUI.commander.getCommandMap().get(i).execute(builder.toString());
                System.out.println(response.getMessage());
            }
        }
    }
}
