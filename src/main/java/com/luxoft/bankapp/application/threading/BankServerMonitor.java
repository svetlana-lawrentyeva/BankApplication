package com.luxoft.bankapp.application.threading;

public class BankServerMonitor implements Runnable{
    @Override
    public void run() {
        while(true){
            System.out.println("queue has " + BankServerThreaded.getCounter() + " waiting clients");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("error: "+e.getMessage());
            }
        }
    }
}
