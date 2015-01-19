package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.service.Command;

import java.util.Scanner;

/**
 * Created by SCJP on 15.01.15.
 */
public class DepositCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("money to deposit:");
        float x = sc.nextFloat();
        bankService.deposit(BankCommander.currentClient, x);
        System.out.println("Account successfully credited");
        System.out.println(bankService.getAccountInfo(BankCommander.currentClient.getActiveAccount()));

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposit");
    }
}
