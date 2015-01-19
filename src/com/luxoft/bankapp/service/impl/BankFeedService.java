package com.luxoft.bankapp.service.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SCJP on 19.01.15.
 */
public class BankFeedService {
    private static BankFeedService bankFeedService;

    private BankFeedService(){}

    public static BankFeedService getInstatnce(){
        if(bankFeedService == null) bankFeedService = new BankFeedService();
        return bankFeedService;
    }

    public void loadFeeds(String folder){
        File dir = new File(folder);
        if(dir.exists()){
            File[]files = dir.listFiles();
            for(File f:files){
                if(f.isDirectory())loadFeeds(f.getName());
                else{
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(f));
                        String line = "";
                        while((line=br.readLine())!=null){
                            parseFeed(line);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void parseFeed(String feed){
        List<Map<String, String>> maps = new ArrayList();
        String []lines = feed.split("\n");
        for(String line:lines){
            Map<String, String> map = new HashMap<String, String>();
            String []parameters = feed.split(";");
            for(String str:parameters){
                String []mapData = str.split("=");
                map.put(mapData[0], mapData[1]);
            }
            BankCommander.currentBank.parseFeed(map);
        }
        for(Map<String, String> map:maps){
            BankCommander.currentBank.parseFeed(map);
        }
    }
}
