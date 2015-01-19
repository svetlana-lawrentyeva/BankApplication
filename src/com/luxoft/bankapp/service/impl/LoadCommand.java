package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.service.Command;

/**
 * Created with IntelliJ IDEA.
 * User: nau
 * Date: 1/19/15
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadCommand implements Command{
    @Override
    public void execute() {
        BankCommander.currentClient = bankService.loadClient();
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Load");
    }
}
