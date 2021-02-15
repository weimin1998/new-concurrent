package com.weimin.threadpool.threadpoolexecutor;

import com.weimin.util.MyUtil;

import java.util.concurrent.SynchronousQueue;

public class Demo1 {
    public static void main(String[] args) {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                synchronousQueue.put(1);
                MyUtil.print("放入1");
                synchronousQueue.put(2);
                MyUtil.print("放入2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                Integer take = synchronousQueue.take();
                MyUtil.print("带走一个"+take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(2000);
                Integer take = synchronousQueue.take();
                MyUtil.print("带走一个"+take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }
}
