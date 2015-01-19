package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.service.Command;

/**
 * Created with IntelliJ IDEA.
 * User: nau
 * Date: 1/19/15
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class SaveCommand implements Command {
    @Override
    public void execute() {
        bankService.saveClient(BankCommander.currentClient);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Save");
    }
}
