package com.weimin.juc.aqs;

import com.weimin.util.MyUtil;

import java.util.concurrent.Semaphore;

/**
 * 信号量
 */
public class SemaphoneTest {
    public static void main(String[] args) {
        //
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MyUtil.print("running");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MyUtil.print("stop");
                semaphore.release();
            }).start();
        }
    }
}
