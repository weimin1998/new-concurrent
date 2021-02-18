package com.weimin.juc.aqs;

import com.weimin.util.MyUtil;

import java.util.concurrent.CountDownLatch;

public class CountDown {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);


        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                MyUtil.print("ready");
                countDownLatch.countDown();
            }).start();
        }




        try {
            countDownLatch.await();
            System.out.println("go..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
