package com.luxoft.bankapp.commander;

public interface Command {

    Object execute(String param);

    String printCommandInfo();
}
