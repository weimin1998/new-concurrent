package com.weimin.juc.aqs;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class AQS {
    public static void main(String[] args) {
        MyLock lock = new MyLock();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                MyUtil.print("加锁成功");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                lock.unlock();
                MyUtil.print("解锁成功");
            }
        },"t1");

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();

                MyUtil.print("加锁成功");
            }finally {
                lock.unlock();
                MyUtil.print("解锁成功");
            }
        },"t2");

        t1.start();
        t2.start();
    }
}


/**
 * 自定义锁
 */
class MyLock implements Lock {

    // 同步器
    private class Sync extends AbstractQueuedSynchronizer {
        // 加锁
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                // 加锁成功，设置owner为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 解锁
        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition condition() {
            return new ConditionObject();
        }
    }


    private Sync sync = new Sync();
    @Override
    public void lock() {
        // 不成功 就进入等待队列
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.condition();
    }
}