package com.luxoft.bankapp;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.impl.BankImpl;
import com.luxoft.bankapp.service.impl.BankCommander;
import com.luxoft.bankapp.service.impl.BankReport;

import java.util.Scanner;

/**
 * Created by SCJP on 15.01.15.
 */
public class BankUI{
    private static BankApplication bankApplication = new BankApplication();

    public static void main(String[] args) {
        if(args.length>0){
            for(String str:args){
                if(str.equals("report")){
                    Bank bank = new BankImpl("report bank");
                    bankApplication.initialize(bank);
                    BankReport bankReport = new BankReport();
                    bankReport.getNumberOfClients(bank);
                    bankReport.getAccountsNumber(bank);
                    bankReport.getClientsSorted(bank);
                    bankReport.getBankCreditSum(bank);
                    bankReport.getClientsByCity(bank);
                }
            }
        } else {
            bankApplication.initialize(BankCommander.currentBank);
            while (true) {
                System.out.println("-----------------------------------------------------");
                if(BankCommander.currentClient!=null)
                    System.out.println("Active client is "+BankCommander.currentClient+"\n");
//                for (int i=0;i<BankCommander.commandMap.length;i++) { // show menu
//                    System.out.print(i+") ");
//                    BankCommander.commands[i].printCommandInfo();
//                }
                for(String str:BankCommander.commandMap.keySet()){
                    System.out.print(str + ". ");
                    BankCommander.commandMap.get(str).printCommandInfo();
                }
                Scanner sc = new Scanner(System.in);
    //            String commandString = System.console().readLine();
//                int command=sc.nextInt(); // initialize command with commandString
//                BankCommander.commands[command].execute();
                String command = sc.nextLine();
                BankCommander.commandMap.get(command).execute();
            }
        }
    }
}
