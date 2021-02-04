package com.weimin.interrupt;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;

/**
 * 打断正在睡眠的线程
 */
public class InterruptSleep {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                MyUtil.print("子线程sleep..");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                MyUtil.print("子线程被打断了..");
                e.printStackTrace();
            }
        });
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 主线程一秒后打断子线程
        thread.interrupt();

        // 打断正常运行的线程 打断标记是true
        // 打断正在sleep的线程 打断标记是false
        System.out.println(thread.isInterrupted());// false

    }
}
