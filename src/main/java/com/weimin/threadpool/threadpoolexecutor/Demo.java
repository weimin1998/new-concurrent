package com.weimin.threadpool.threadpoolexecutor;

import com.weimin.util.MyUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Demo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.execute(()->{
            MyUtil.print("haha");
        });
        pool.execute(()->{
            MyUtil.print("haha");
        });
        pool.execute(()->{
            MyUtil.print("haha");
        });


        List<Future<Object>> futures = pool.invokeAll(Arrays.asList(
                () -> {
                    return 1;
                },
                () -> {
                    return 2;
                },
                () -> {
                    return 3;
                }
        ));

        for (Future<Object> future : futures) {
            System.out.println(future.get());
        }


        Object o = pool.invokeAny(Arrays.asList(
                () -> {
                    return 4;
                },
                () -> {
                    return 5;
                },
                () -> {
                    return 6;
                }
        ));
        System.out.println(o);



        System.out.println(pool.isShutdown());
        System.out.println(pool.isTerminated());
        pool.shutdown();

        System.out.println(pool.isShutdown());
        System.out.println(pool.isTerminated());

    }
}
