package com.luxoft.bankapp.commander;

public interface Command {

    Response execute(String param);

    String printCommandInfo();
}
