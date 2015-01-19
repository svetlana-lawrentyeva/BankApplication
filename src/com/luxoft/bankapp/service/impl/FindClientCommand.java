package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.impl.ClientExistsException;
import com.luxoft.bankapp.model.impl.ClientNotExistsException;
import com.luxoft.bankapp.service.Command;

import java.util.Scanner;

/**
 * Created by SCJP on 15.01.15.
 */
public class FindClientCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("name to search:");
        String name = sc.nextLine();
        Client client = null;
        try {
            client = bankService.findClient(name);
        } catch (ClientNotExistsException e) {
            System.out.println(e.getMessage());
        }
        BankCommander.currentClient = client;
        System.out.println("Client "+client+"is checked\n");
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Find client");
    }
}
