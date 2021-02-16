package com.weimin.threadpool.scheduled;

import com.weimin.util.MyUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) {
        // 类似固定大小的线程池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        pool.schedule(()->{
            MyUtil.print("任务1");
            try {
                int a = 10/0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        },1, TimeUnit.SECONDS);

        pool.schedule(()->{
            MyUtil.print("任务2");
        },1, TimeUnit.SECONDS);

        // 以固定的速率执行任务
        // 一秒以后 每间隔一秒 就执行一遍
        // 如果 任务本身的执行时间大于一秒，比如是两秒，那么任务的间隔就会变为两秒，间隔挤长了
        /*pool.scheduleAtFixedRate(()->{
            MyUtil.print("反复任务");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1,1,TimeUnit.SECONDS);*/

        /*pool.scheduleWithFixedDelay(()->{
            MyUtil.print("反复任务");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1,1,TimeUnit.SECONDS);*/

    }
}
