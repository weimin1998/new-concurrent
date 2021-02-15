package com.weimin.threadpool.scheduled;

import com.weimin.util.MyUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 一秒后 执行两个任务，如果任务一没有延时两秒，就是正常的运行，一秒后执行两个任务
 * 但是任务一延迟，任务2 就不是一秒后运行了，而是把任务2挤到两秒后了
 */
public class timer {
    public static void main(String[] args) {
        // 创建一个timer
        Timer timer = new Timer();

        // 两个任务
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                MyUtil.print("任务1");
                int a = 10/0;
                /*try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        };

        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                MyUtil.print("任务2");
            }
        };


        // 一秒后执行两个任务
        timer.schedule(timerTask1,1000);
        timer.schedule(timerTask2,1000);
    }

}
