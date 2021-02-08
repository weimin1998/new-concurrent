package com.weimin.guancheng.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 */
public class Fair {
    private static Lock lock = new ReentrantLock(true);
    public static void main(String[] args) {

    }
}
