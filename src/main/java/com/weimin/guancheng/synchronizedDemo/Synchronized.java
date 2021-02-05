package com.weimin.guancheng.synchronizedDemo;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;

/**
 * 锁对象不同，不会互斥
 */
public class Synchronized {
    public static void main(String[] args) {
        Number number = new Number();

        Thread a = new Thread(() -> {
            Number.a();
        }, "a");

        Thread b = new Thread(() -> {
            number.b();
        }, "b");

        a.start();
        b.start();
    }
}

class Number{
    public static synchronized void a(){

        MyUtil.print("a..");
    }
    public  synchronized void b(){
        MyUtil.print("b..");
    }
}