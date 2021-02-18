package com.weimin.threadpool.forkjoin;

import com.weimin.util.MyUtil;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 求1-n的和
 */
public class Forkjoindemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        Integer invoke = forkJoinPool.invoke(new Task(5));

        System.out.println(invoke);

    }
}

// RecursiveTask 递归任务，有返回值
class Task extends RecursiveTask<Integer>{

    private int n;

    public Task(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if(n==1){
            return 1;
        }
        Task task = new Task(n - 1);

        task.fork();
        MyUtil.print("fork");

        Integer join = task.join();
        MyUtil.print(join);


        return n + join;
    }
}
