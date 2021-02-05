package com.weimin.interrupt;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 打断park状态的线程，打断标记会置为true.
 * 被打断后 就不会再被park() 暂停了
 */
public class InterruptPark {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            MyUtil.print("park..");
            LockSupport.park();

            MyUtil.print("unpark..");
            MyUtil.print(Thread.currentThread().isInterrupted());
        });

        thread.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
