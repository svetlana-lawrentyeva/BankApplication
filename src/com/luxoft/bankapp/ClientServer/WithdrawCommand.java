package com.luxoft.bankapp.ClientServer;

import com.luxoft.bankapp.model.impl.BankException;

import java.util.Scanner;

/**
 * Created by SCJP on 15.01.15.
 */
public class WithdrawCommand implements Command {
    @Override
    public String execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("money to withdraw:");
        float x = sc.nextFloat();
        System.out.println("Account successfully withdrawn");
        try {
            bankService.withdraw(ServerCommander.currentClient, x);
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
        return (bankService.getAccountInfo(ServerCommander.currentClient.getActiveAccount()));
    }
}
