package com.luxoft.bankapp.service;

import com.luxoft.bankapp.dao.impl.DaoFactory;
import com.luxoft.bankapp.model.exceptions.BankException;
import com.luxoft.bankapp.model.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ServiceOld {

    public void loadFeeds(Bank bank, String folder) throws BankException {
        File dir = new File(folder);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        loadFeeds(bank, f.getName());
                    } else {
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(f));
                            String line = "";
                            while ((line = br.readLine()) != null) {
                                parseFeed(bank, line);
                            }
                            br.close();
                        } catch (Exception e) {
                            throw  new BankException(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public Client parseFeed(Bank bank, String feed) throws ClientExistsException {

        Map<String, String> map = new HashMap<>();
        String[] parameters = feed.split(";");
        for (String str : parameters) {
            String[] mapData = str.split("=");
            map.put(mapData[0], mapData[1]);
        }
        return bank.parseFeed(map);
    }

    public BankInfo getBankInfo(Bank bank){
        return DaoFactory.getBankDao().getBankInfo(bank);
    }
}
