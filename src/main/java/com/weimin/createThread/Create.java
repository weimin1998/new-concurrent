package com.weimin.createThread;

import com.weimin.util.MyUtil;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Create {

    @Test
    public void test(){
        // callable
        FutureTask<Integer> integerFutureTask = new FutureTask<>(() -> {
            Thread.sleep(1000);
            int a = 10/0;
            return 100;
        });

        Thread thread = new Thread(integerFutureTask);

        thread.start();

        try {
            // get()会阻塞
            Integer integer = integerFutureTask.get();
            MyUtil.print(integer);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
