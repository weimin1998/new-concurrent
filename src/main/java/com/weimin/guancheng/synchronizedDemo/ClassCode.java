package com.weimin.guancheng.synchronizedDemo;

/**
 * 字节码的角度
 */
public class ClassCode {
    static final Object lock = new Object();
    static int counter = 0;

    public static void main(String[] args) {
        synchronized (lock){
            counter++;
        }
    }
}
