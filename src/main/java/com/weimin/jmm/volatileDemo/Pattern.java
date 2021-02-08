package com.weimin.jmm.volatileDemo;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;

public class Pattern {


    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        monitor.start();
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

    private volatile boolean isStop = false;

    private boolean isStarting = false;

    public void start() {
        // 犹豫模式
        synchronized (this){
            if(isStarting){
                return;
            }
            isStarting = true;
        }

        thread = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();

                if (isStop) {
                    MyUtil.print("善后。。");
                    break;
                }
                try {
                    MyUtil.print("执行监控任务。。");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }

            }
        });

        thread.start();
    }

    public void stop() {
        isStop = true;
        // 如果正在sleep的时间比较长，但是想让现在就停止
        thread.interrupt();
    }
}
