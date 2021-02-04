package com.weimin.join;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;

public class Join {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                MyUtil.print("子线程代码。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");


        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                MyUtil.print("子线程代码。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");

        t1.start();
        t2.start();

        MyUtil.print("子线程加入。。");
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MyUtil.print("子线程执行完毕。。");

    }
}
