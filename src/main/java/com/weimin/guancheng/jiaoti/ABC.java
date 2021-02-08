package com.weimin.guancheng.jiaoti;

import com.weimin.util.MyUtil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ABC {
    static boolean flag = true;

    public static void main(String[] args) {

        Print print = new Print();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                print.printA();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                print.printB();
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                print.printC();
            }
        });

        t1.start();
        t2.start();
        t3.start();


    }
}

class Print{
    private static Lock lock = new ReentrantLock();
    private int flag = 1;
    private Condition A = lock.newCondition();
    private Condition B = lock.newCondition();
    private Condition C = lock.newCondition();

    public void printA(){
        try {
            lock.lock();

            while (flag!=1){
                try {
                    A.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("a");
            flag = 2;
            B.signal();
        }finally {
            lock.unlock();
        }

    }

    public void printB(){
        try {
            lock.lock();

            while (flag!=2){
                try {
                    B.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("b");
            flag = 3;
            C.signal();
        }finally {
            lock.unlock();
        }

    }

    public void printC(){
        try {
            lock.lock();

            while (flag!=3){
                try {
                    C.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("c");
            flag = 1;
            A.signal();
        }finally {
            lock.unlock();
        }

    }


}


