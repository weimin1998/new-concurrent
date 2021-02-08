package com.weimin.guancheng.reentrantlock;

import com.weimin.util.MyUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入
 */
public class Reentrant {
    private static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        try {
            lock.lock();
            MyUtil.print("第一次获得锁");
            m1();
        }finally {
            lock.unlock();
        }
    }

    private static void m1() {
        try {
            lock.lock();
            MyUtil.print("第二次获得锁");
            m2();
        }finally {
            lock.unlock();
        }
    }

    private static void m2() {
        try {
            lock.lock();
            MyUtil.print("第三次获得锁");
        }finally {
            lock.unlock();
        }
    }


}
