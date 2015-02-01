package com.luxoft.bankapp.commander;

public interface Command {

    void execute();

    String printCommandInfo();
}
