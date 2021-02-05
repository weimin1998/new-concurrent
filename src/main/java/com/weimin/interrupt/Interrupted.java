package com.weimin.interrupt;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Thread.interrupted() 返回打断标记 并且把打断标记置为false.
 * 这样线程就又可以park()暂停
 */

public class Interrupted {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            MyUtil.print("park..");
            LockSupport.park();

            MyUtil.print("unpark..");

            MyUtil.print(Thread.interrupted());

            LockSupport.park();

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
