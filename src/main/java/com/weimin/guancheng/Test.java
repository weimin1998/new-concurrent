package com.weimin.guancheng;

/**
 * 自增自减 不是原子操作
 */
public class Test {
    static int num = 0;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                num++;
            }
        },"t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                num--;
            }
        },"t2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(num);
    }
}
