package com.weimin.threadpool.threadpoolexecutor;

import com.weimin.util.MyUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * 如果单线程池 执行任务发生异常 线程结束了 ，他会再重新创建一个线程顶替原来的线程继续执行其他任务。
 */
public class Demo2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(()->{
            int a = 10/0;
        });
        executorService.execute(()->{
            MyUtil.print("haha");
        });
        executorService.execute(()->{
            MyUtil.print("haha");
        });

    }
}
