package com.weimin.guancheng.reentrantlock;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁超时
 */
public class Timeout {
    private static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                if(!lock.tryLock(3, TimeUnit.SECONDS)){
                    MyUtil.print("没有获取到锁");
                    return;
                }

            }catch (Exception e){
                MyUtil.print("在等待获得锁的时候被打断了,没有获得锁");
                return;
            }

            try {
                MyUtil.print("获取到锁");
            }finally {
                lock.unlock();
            }
        });



        lock.lock();
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
