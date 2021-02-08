package com.weimin.guancheng;

import com.weimin.util.MyUtil;

import java.util.concurrent.locks.LockSupport;

/**
 * park unpark
 */
public class ParkUnpark {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            MyUtil.print("start");
            MyUtil.print("park");
            LockSupport.park();

            MyUtil.print("unpark");

        }, "t1");

        thread.start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockSupport.unpark(thread);
    }
}
