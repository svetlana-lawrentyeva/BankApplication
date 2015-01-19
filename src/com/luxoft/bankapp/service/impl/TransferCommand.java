package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.impl.BankException;
import com.luxoft.bankapp.model.impl.ClientNotExistsException;
import com.luxoft.bankapp.service.Command;

import java.util.Scanner;

/**
 * Created by SCJP on 15.01.15.
 */
public class TransferCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("name to transfer:");
        String name = sc.nextLine();
        Client client = null;
        try {
            client = bankService.findClient(name);
        } catch (ClientNotExistsException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("money to transfer:");
        float x = sc.nextFloat();
        try {
            bankService.transfer(BankCommander.currentClient, client, x);
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer");
    }
}
