package com.weimin.interrupt;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;

/**
 * 打断正在 正常运行的线程
 */
public class Interrupt {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
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

        // 打断正常运行的线程 打断标记是true,但是只是提醒 线程 ，有人要打断你，至于是否终止运行，自己决定
        // 打断正在sleep的线程 打断标记是false
        System.out.println(thread.isInterrupted());// true

    }
}
