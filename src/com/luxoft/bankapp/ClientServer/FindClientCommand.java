package com.luxoft.bankapp.ClientServer;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.impl.ClientNotExistsException;

import java.util.Scanner;

/**
 * Created by SCJP on 15.01.15.
 */
public class FindClientCommand implements Command {
    @Override
    public String execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("name to search:");
        String name = sc.nextLine();
        Client client = null;
        try {
            client = bankService.findClient(name);
        } catch (ClientNotExistsException e) {
            System.out.println(e.getMessage());
        }
        ServerCommander.currentClient = client;
        return ("Client "+client+"is checked\n");
    }
}
