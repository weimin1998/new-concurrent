package com.weimin.juc.aqs;

import com.weimin.util.MyUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDown1 {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        ExecutorService pool = Executors.newFixedThreadPool(6);

        pool.submit(()->{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MyUtil.print("go..");
        });


        for (int i = 0; i < 5; i++) {
            pool.submit(()->{
                MyUtil.print("ready");
                countDownLatch.countDown();
            });
        }







    }
}
