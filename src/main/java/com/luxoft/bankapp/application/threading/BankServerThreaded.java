package com.luxoft.bankapp.application.threading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class BankServerThreaded {
    public static int PORT = 2579;
    private static int POOL_SIZE = 10;
    private ServerSocket serverSocket;
    private ExecutorService pool;
    private boolean running = true;
    private static AtomicInteger counter = new AtomicInteger(0);
    private static Logger log = Logger.getLogger(BankServerThreaded.class.getName());

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
        log.info("server is starting...");
        try {
            serverSocket = new ServerSocket(PORT);
            log.fine("serverSocket has created");
            pool = Executors.newFixedThreadPool(POOL_SIZE);
            log.fine("pool for clients threads has created");

            while (running) {
                Socket clientSocket = serverSocket.accept();
                log.info("connection to a client created");
                getCounter().incrementAndGet();
                log.info("the client starts his own thread");
                pool.execute(new ServerThread(clientSocket, String.valueOf(clientSocket.hashCode()*Math.random()*12)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
        log.info("server stops");
    }

}
