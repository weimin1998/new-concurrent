package com.weimin.threadpool.scheduled;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务   每周四执行
 * 思路：
 * 先获取当前时间，如果是周四之前，就计算和本周四的间隔时间
 */
public class Demo {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        System.out.println(now);

        // 周四10：00
        LocalDateTime with = now.withHour(10).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);


        if(now.compareTo(with)>0){
            with = with.plusWeeks(1);
        }
        System.out.println(with);

        // 一周有多少毫秒
        long period = 7*24*60*60*1000;

        Duration between = Duration.between(now, with);
        long l = between.toMillis();
        pool.scheduleAtFixedRate(()->{
            System.out.println("定时任务");
        },l,period, TimeUnit.MILLISECONDS);

    }
}
