package com.weimin.guancheng.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件变量
 */
public class ConditionDemo {
    private static Lock lock = new ReentrantLock();

    private static boolean flag = false;
    public static void main(String[] args) {
        Condition number = lock.newCondition();
        Condition letter = lock.newCondition();

        Thread t1 = new Thread(() -> {

            try {
                lock.lock();

                for (int i = 1; i <= 26; i++) {
                    while (flag){
                        try {
                            number.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.print(2*i-1);
                    System.out.print(2*i);
                    flag=true;
                    letter.signal();
                }
            }finally {
                lock.unlock();
            }



        });


        Thread t2= new Thread(() -> {

            try {
                lock.lock();

                for (int i = 1; i <= 26; i++) {
                    while (!flag){
                        try {
                            letter.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.print((char)(i+96) );
                    flag = false;
                    number.signal();
                }
            }finally {
                lock.unlock();
            }



        });


        t1.start();
        t2.start();
    }
}
