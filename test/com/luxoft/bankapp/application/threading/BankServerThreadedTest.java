package com.luxoft.bankapp.application.threading;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by SCJP on 06.02.15.
 */
public class BankServerThreadedTest {

    @Test
    public void clientWithdrawTest(){
        for(int i = 0;i<10;++i){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
            new Thread(new BankClientMock(i)).start();
        }
    }

}
