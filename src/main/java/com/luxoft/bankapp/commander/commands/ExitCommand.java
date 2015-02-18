package com.luxoft.bankapp.commander.commands;

import com.luxoft.bankapp.application.io.Io;
import com.luxoft.bankapp.commander.AbstractCommand;

/**
 * Created by nau on 18.02.15.
 */
public class ExitCommand extends AbstractCommand {
    @Override
    public void execute(Io io) {
        io.write("stop");
    }

    @Override
    public String printCommandInfo() {
        return "Exit";
    }
}
