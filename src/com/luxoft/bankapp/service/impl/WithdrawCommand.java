package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.model.impl.BankException;
import com.luxoft.bankapp.service.Command;

import java.util.Scanner;

/**
 * Created by SCJP on 15.01.15.
 */
public class WithdrawCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("money to withdraw:");
        float x = sc.nextFloat();
        System.out.println("Account successfully withdrawn");
        try {
            bankService.withdraw(BankCommander.currentClient, x);
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(bankService.getAccountInfo(BankCommander.currentClient.getActiveAccount()));
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Withdraw");
    }
}
