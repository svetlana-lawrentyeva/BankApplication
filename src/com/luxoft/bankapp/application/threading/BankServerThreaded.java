package com.luxoft.bankapp.application.threading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BankServerThreaded {
    public static int PORT = 2579;
    private static int POOL_SIZE = 10;
    private ServerSocket serverSocket;
    private ExecutorService pool;
    private boolean running = true;
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        BankServerThreaded bankServerThreaded = new BankServerThreaded();
        Thread monitor = new Thread(new BankServerMonitor());
        monitor.setDaemon(true);
        monitor.start();
        bankServerThreaded.start();
    }

    public static AtomicInteger getCounter() {
        return counter;
    }

    public static void setCounter(AtomicInteger counter) {
        BankServerThreaded.counter = counter;
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(PORT);
            pool = Executors.newFixedThreadPool(POOL_SIZE);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                getCounter().incrementAndGet();
                pool.execute(new ServerThread(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

}
