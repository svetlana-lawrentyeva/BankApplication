package com.luxoft.bankapp.commander;

import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by SCJP on 27.01.15.
 */
public interface Commander {
    public Client getCurrentClient();

    public void setCurrentClient(Client currentClient);

    public Bank getCurrentBank();

    public void setCurrentBank(Bank currentBank);

    public void registerCommand(Integer name, Command command);

    public void removeCommand(String name);

    public void setCommandMap(Map<Integer, Command> map);

    public Map<Integer, Command> getCommandMap();

    public List<String> getCommandRequest();
}
