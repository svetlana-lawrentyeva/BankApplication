package com.luxoft.bankapp.commander;

import com.luxoft.bankapp.application.io.Io;

public interface Command {

    void execute(Io io);

    String printCommandInfo();
}
