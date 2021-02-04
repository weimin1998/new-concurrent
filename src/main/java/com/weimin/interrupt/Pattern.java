package com.weimin.interrupt;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;

/**
 * 设计模式
 * 两阶段终止
 * 优雅的终止一个线程
 * 要终止一个线程，不要直接stop(),因为这个线程可能还有事情没有完成，比如还有锁没有释放。。
 * 也不要System.exit(), 因为这样直接结束进程了
 */
public class Pattern {
    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        monitor.start();

        TimeUnit.SECONDS.sleep(6);

        monitor.stop();
    }


}

/**
 * 一个监控系统，一秒执行一次监控
 * 当要停止监控时，如何优雅的结束
 */
class Monitor {
    Thread thread;

    public void start() {
        thread = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();

                if (currentThread.isInterrupted()) {
                    MyUtil.print("善后。。");
                    break;
                }
                try {
                    MyUtil.print("执行监控任务。。");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    currentThread.interrupt();// 打断标记置为true
                }

            }
        });

        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }
}
