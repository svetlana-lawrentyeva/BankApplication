package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.service.Command;

/**
 * Created by SCJP on 19.01.15.
 */
public class BankFeedCommand implements Command {
    @Override
    public void execute() {
        BankFeedService.getInstatnce().loadFeeds("./feeds");
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Feed");
    }
}
