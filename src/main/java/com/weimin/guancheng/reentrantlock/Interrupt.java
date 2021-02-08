package com.weimin.guancheng.reentrantlock;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可打断
 *  lock.lockInterruptibly();
 */
public class Interrupt {
    private static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                MyUtil.print("尝试获得锁");
                lock.lockInterruptibly();

            } catch (InterruptedException e) {
                MyUtil.print("没有获得锁，返回");
                return;

            }


            try {
                MyUtil.print("获得到锁");
            }finally {
                lock.unlock();
            }
        });



        // 主线程先获得锁
        lock.lock();
        thread.start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();

    }
}
