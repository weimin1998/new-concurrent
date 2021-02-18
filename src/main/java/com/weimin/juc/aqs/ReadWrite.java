package com.weimin.juc.aqs;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWrite {
    public static void main(String[] args) {

    }
}

class DataContainer{
    private Object data;
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    // 读锁
    private ReentrantReadWriteLock.ReadLock readLock= readWriteLock.readLock();
    // 写锁
    private ReentrantReadWriteLock.WriteLock writeLock= readWriteLock.writeLock();


    public Object read() {
        return data;
    }
}
